package com.scoreit.hockeyscorekeeper.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.model.GameShots;

import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GameRepository {
    private GameDao mGameDao;
    private GameShotsDao mGameShotsDao;
    private GameShotsRepository mGameShotsRepository;
    private GameScoringDao mGameScoringDao;
    private HockeyDatabase mDb;

    public GameRepository(Application application) {
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        mGameDao = db.getGameDao();
        mGameShotsDao = db.getGameShotsDao();
        mGameShotsRepository = new GameShotsRepositoryImpl(mGameShotsDao);

    }

//    public LiveData<List<Team>> getGameReadyTeams() {
//        LiveData<List<RosterCount>> eligibleRosters = mRosterCountDao.eligibleTeamIds(5);
//
//        LiveData<List<Team>> teams = Transformations.switchMap(eligibleRosters,
//                new Function<List<RosterCount>, LiveData<List<Team>>>() {
//                    @Override
//                    public LiveData<List<Team>> apply(List<RosterCount> input) {
//                        int[] teamIds = new int[input.size()];
//
//                        for (int i=0; i<input.size();i++){
//                            teamIds[i] = input.get(i).teamId;
//                        }
//
//                        return mTeamDao.getGameEligibleTeams(teamIds);
//                    }
//                });
//
//        return teams;
//    }

//    public LiveData<Team> getTeam (int teamId) {
//        return mTeamRepository.getTeam(teamId);
//    }

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
                mGameShotsRepository.addGameShots(shots);
                //new addAsyncGameShots(mGameShotsDao, shots).execute();
                return new Long(gameId);
            }
        }).subscribeOn(Schedulers.io());
    }

    public void updateGame(Game game){
        new updateAsyncGame(mGameDao, game).execute();
    }

    public LiveData<GameShots> getGameShots(long id) {
        return mGameShotsDao.getGameShots(id);
    }

    public void updateGameShots(GameShots gameShots) {
        new updateAsyncGameShots(mGameShotsDao, gameShots).execute();
    }

    public Single<Long> addGameScoring(GameScoring goal) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long gameId = mGameScoringDao.insertAll(goal);
                return new Long(gameId);
            }
        }).subscribeOn(Schedulers.io());
    }

    private static class updateAsyncGame extends AsyncTask<Void, Void, Boolean> {
        private GameDao mAsyncTaskDao;
        private Game mGame;


        public updateAsyncGame(GameDao dao, Game game) {
            mAsyncTaskDao = dao;
            mGame = game;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.update(mGame);
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

    private static class updateAsyncGameShots extends AsyncTask<Void, Void, Boolean> {
        private GameShotsDao mAsyncTaskDao;
        private GameShots mGameShots;

        updateAsyncGameShots(GameShotsDao dao, GameShots gameshots) {
            mAsyncTaskDao = dao;
            mGameShots = gameshots;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            mAsyncTaskDao.update(mGameShots);
            return true;
        }
    }
}
