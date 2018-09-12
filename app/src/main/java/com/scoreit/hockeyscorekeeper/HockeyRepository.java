package com.scoreit.hockeyscorekeeper;

import android.app.Application;
import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.CoachDao;
import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameLineupDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.PlayerDao;
import com.scoreit.hockeyscorekeeper.data.RosterCountDao;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.Coach;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameLineup;
import com.scoreit.hockeyscorekeeper.model.GameShots;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.RosterCount;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class HockeyRepository {
    private TeamDao mTeamDao;
    private PlayerDao mPlayerDao;
    private CoachDao mCoachDao;
    private RosterCountDao mRosterCountDao;
    private GameDao mGameDao;
    private GameLineupDao mGameLineupDao;
    private GameShotsDao mGameShotsDao;

    private LiveData<List<Team>> mAllTeams;
    private LiveData<List<String>> mGameEligibleTeams;
    private LiveData<List<RosterCount>> mGameEligibleTeamsIds;
    public IAsyncTaskResults<Long> addTeamDelegate = null;

    public HockeyRepository(Application application) {
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        mTeamDao = db.getTeamDao();
        mPlayerDao = db.getPlayerDao();
        mCoachDao = db.getCoachDao();
        mRosterCountDao = db.getRosterCountDao();
        mGameDao = db.getGameDao();
        mGameLineupDao = db.getGameLineupDao();
        mGameShotsDao = db.getGameShotsDao();
        mAllTeams = mTeamDao.getAll();
    }

    /*
     *  Team Operations
     */
    public LiveData<List<Team>> getAllTeams() {
        return mAllTeams;
    }

    public LiveData<List<Team>> getGameReadyTeams() {
        LiveData<List<RosterCount>> eligibleRosters = mRosterCountDao.eligibleTeamIds(5);

        LiveData<List<Team>> teams = Transformations.switchMap(eligibleRosters,
                new Function<List<RosterCount>, LiveData<List<Team>>>() {
                    @Override
                    public LiveData<List<Team>> apply(List<RosterCount> input) {
                        int[] teamIds = new int[input.size()];

                        for (int i=0; i<input.size();i++){
                            teamIds[i] = input.get(i).teamId;
                        }

                        return mTeamDao.getGameEligibleTeams(teamIds);
                    }
                });

        return teams;
    }

    public LiveData<Team> getTeam (int teamId) {
        return mTeamDao.getTeam(teamId);
    }

    public void insert (Team team)  {
        new insertAsyncTask(mTeamDao, addTeamDelegate).execute(team);
    }

    public Single<Long> addTeam (final Team team) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return new Long(mTeamDao.insert(team));
            }
        }).subscribeOn(Schedulers.io());
    }

    public void removeTeam(final Team team){
        new removeAsyncTeam(mTeamDao, team).execute();
    }

    public void updateTeam(final Team team){
        new updateAsyncTeam(mTeamDao, team).execute();
    }

    /*
     *  Player Operations
     */

    public LiveData<List<Player>> getAllPlayers() {
        return mPlayerDao.getAll();
    }

    public LiveData<Player> getPlayer (int teamId, int playerId) {
        return mPlayerDao.getPlayer(teamId, playerId);
    }

    public LiveData<List<Player>> getTeamPlayers(int teamId) {
        return mPlayerDao.getPlayersByTeam(teamId);
    }

    public void addPlayer (Player player) {
        new addAsyncPlayer(mPlayerDao, player).execute();
    }

    public void removePlayer(Player player) { new removeAsyncPlayer(mPlayerDao, player).execute(); }

    public void updatePlayer(Player player) { new updateAsyncPlayer(mPlayerDao, player).execute(); }

    /*
     *  Coach Operations
     */

    public LiveData<List<Coach>> getAllCoaches() {
        return mCoachDao.getAll();
    }

    public LiveData<List<Coach>> getTeamCoaches(int teamId) {
        return mCoachDao.getCoachesByTeam(teamId);
    }

    public LiveData<Coach> getCoach(int teamId, int coachId) {
        return mCoachDao.getCoach(teamId, coachId);
    }

    public void addCoach(Coach coach) {
        new addAsyncCoach(mCoachDao, coach).execute();
    }

    public void removeCoach(Coach coach) {
        new removeAsyncCoach(mCoachDao, coach).execute();
    }

    public void updateCoach(Coach coach) {
        new updateAsyncCoach(mCoachDao, coach).execute();
    }

    /*
     *  Game Operations
     */
    public LiveData<Game> getGame(long gameId){
        return mGameDao.getGame(gameId);
    }

    public Single<Long> addGame(Game game){
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long gameId = mGameDao.insert(game);
                GameShots shots = new GameShots(gameId);
                new addAsyncGameShots(mGameShotsDao, shots).execute();
                return new Long(gameId);
            }
        }).subscribeOn(Schedulers.io());
    }

    public void updateGame(Game game){
        mGameDao.update(game);
    }

    public void addGameLineup(ArrayList lineup) {
        new addAsyncGameLineup(mGameLineupDao, lineup).execute();
    }

    public LiveData<GameShots> getGameShots(long id) {
        return mGameShotsDao.getGameShots(id);
    }

    public void addGameShots(GameShots gameShots){
        mGameShotsDao.insert(gameShots);
    }

    public void updateGameShots(GameShots gameShots) {
        mGameShotsDao.update(gameShots);
    }

    private static class updateAsyncPlayer extends AsyncTask<Void, Void, Boolean> {
        private PlayerDao mAsyncTaskDao;
        private Player mPlayer;


        public updateAsyncPlayer(PlayerDao dao, Player player) {
            mAsyncTaskDao = dao;
            mPlayer = player;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.update(mPlayer);
            return true;
        }

    }

    private static class removeAsyncPlayer extends AsyncTask<Void, Void, Boolean> {
        private PlayerDao mAsyncTaskDao;
        private Player mPlayer;


        public removeAsyncPlayer(PlayerDao dao, Player player) {
            mAsyncTaskDao = dao;
            mPlayer = player;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.delete(mPlayer);
            return true;
        }

    }

    private static class addAsyncPlayer extends AsyncTask<Void, Void, Boolean> {
        private PlayerDao mAsyncTaskDao;
        private Player mPlayer;

        addAsyncPlayer(PlayerDao dao, Player player) {
            mAsyncTaskDao = dao;
            mPlayer = player;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.insert(mPlayer);
            return true;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Team, Void, Long> {

        private TeamDao mAsyncTaskDao;
        private IAsyncTaskResults<Long> mAddTeamListener;

        insertAsyncTask(TeamDao dao, IAsyncTaskResults<Long> asyncTaskListener) {
            mAsyncTaskDao = dao;
            mAddTeamListener = asyncTaskListener;
        }

        @Override
        protected Long doInBackground(final Team... params) {
            long result = mAsyncTaskDao.insert(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Long addedId){
            if (mAddTeamListener != null) {
                mAddTeamListener.onItemAdded(addedId);
            }
        }
    }

    private static class updateAsyncTeam extends AsyncTask<Void, Void, Boolean> {
        private TeamDao mAsyncTaskDao;
        private Team mTeam;

        updateAsyncTeam(TeamDao dao, Team team) {
            mAsyncTaskDao = dao;
            mTeam = team;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.update(mTeam);
            return true;
        }
    }

    private static class removeAsyncTeam extends AsyncTask<Void, Void, Boolean> {
        private TeamDao mAsyncTaskDao;
        private Team mTeam;


        public removeAsyncTeam(TeamDao dao, Team team) {
            mAsyncTaskDao = dao;
            mTeam = team;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.delete(mTeam);
            return true;
        }

    }

    private static class addAsyncCoach extends AsyncTask<Void, Void, Boolean> {
        private CoachDao mAsyncTaskDao;
        private Coach mCoach;

        addAsyncCoach(CoachDao dao, Coach coach) {
            mAsyncTaskDao = dao;
            mCoach = coach;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.insert(mCoach);
            return true;
        }
    }

    private static class updateAsyncCoach extends AsyncTask<Void, Void, Boolean> {
        private CoachDao mAsyncTaskDao;
        private Coach mCoach;

        updateAsyncCoach(CoachDao dao, Coach coach) {
            mAsyncTaskDao = dao;
            mCoach = coach;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            mAsyncTaskDao.update(mCoach);
            return true;
        }
    }

    private static class removeAsyncCoach extends AsyncTask<Void, Void, Boolean> {
        private CoachDao mAsyncTaskDao;
        private Coach mCoach;

        removeAsyncCoach(CoachDao dao, Coach coach) {
            mAsyncTaskDao = dao;
            mCoach = coach;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            mAsyncTaskDao.delete(mCoach);
            return true;
        }
    }

    private static class addAsyncGame extends AsyncTask<Game, Void, Long> {

        private GameDao mAsyncTaskDao;
        private IAsyncTaskResults<Long> mAddGameListener;

        addAsyncGame(GameDao dao, IAsyncTaskResults<Long> asyncTaskListener) {
            mAsyncTaskDao = dao;
            mAddGameListener = asyncTaskListener;
        }

        @Override
        protected Long doInBackground(final Game... params) {
            long result = mAsyncTaskDao.insert(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Long addedId) {
            if (mAddGameListener != null) {
                mAddGameListener.onItemAdded(addedId);
            }
        }
    }

    private static class addAsyncGameLineup extends AsyncTask<Void, Void, Boolean> {
        private GameLineupDao mAsyncTaskDao;
        private List<GameLineup> mGameLineup;

        addAsyncGameLineup(GameLineupDao dao, List<GameLineup> gameLineup) {
            mAsyncTaskDao = dao;
            mGameLineup = gameLineup;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.insertAll(mGameLineup);
            return true;
        }
    }

    private static class addAsyncGameShots extends AsyncTask<Void, Void, Boolean> {
        private GameShotsDao mAsyncTaskDao;
        private GameShots mGameShots;

        addAsyncGameShots(GameShotsDao dao, GameShots gameshots) {
            mAsyncTaskDao = dao;
            mGameShots = gameshots;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.insert(mGameShots);
            return true;
        }
    }

}
