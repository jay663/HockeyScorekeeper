package com.scoreit.hockeyscorekeeper;

import android.app.Application;
import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.PlayerDao;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class HockeyRepository {
    private TeamDao mTeamDao;
    private PlayerDao mPlayerDao;
    private LiveData<List<Team>> mAllTeams;
    public IAsyncTaskResults<Long> addTeamDelegate = null;

    public HockeyRepository(Application application) {
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        mTeamDao = db.getTeamDao();
        mPlayerDao = db.getPlayerDao();
        mAllTeams = mTeamDao.getAll();
    }

    public LiveData<List<Team>> getAllTeams() {
        return mAllTeams;
    }
    public LiveData<List<Player>> getAllPlayers() {
        return mPlayerDao.getAll();
    }

    public LiveData<List<Player>> getTeamPlayers(int teamId) {
        return mPlayerDao.getPlayersByTeam(teamId);
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
        mTeamDao.delete(team);
    }

    public void updateTeam(final Team team){
        mTeamDao.update(team);
    }

    public void addPlayer (Player player) {
        new addAsyncPlayer(mPlayerDao, player).execute();
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
                mAddTeamListener.onTeamAdded(addedId);
            }
        }
    }
}
