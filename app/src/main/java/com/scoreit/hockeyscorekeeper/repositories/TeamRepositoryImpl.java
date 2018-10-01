package com.scoreit.hockeyscorekeeper.repositories;

import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.IAsyncTaskResults;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TeamRepositoryImpl implements TeamRepository {
    private TeamDao mTeamDao;
    private IAsyncTaskResults<Long> addTeamDelegate = null;

    public TeamRepositoryImpl(TeamDao teamDao) {
        mTeamDao = teamDao;
    }

    @Override
    public void addOnClickListener(IAsyncTaskResults<Long> delegate) {
        addTeamDelegate = delegate;
    }

    @Override
    public void removeOnClickListener() {
        addTeamDelegate = null;
    }

    @Override
    public LiveData<List<Team>> getAllTeams() {
        return mTeamDao.getAll();
    }

    @Override
    public LiveData<Team> getTeam(int teamId) {
        return mTeamDao.getTeam(teamId);
    }

    @Override
    public void insert(Team team) {
        new insertAsyncTask(mTeamDao, addTeamDelegate).execute(team);
    }

    @Override
    public Single<Long> addTeam(Team team) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return new Long(mTeamDao.insert(team));
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public void removeTeam(Team team) {
        new removeAsyncTeam(mTeamDao, team).execute();
    }

    @Override
    public void updateTeam(Team team) {
        new updateAsyncTeam(mTeamDao, team).execute();
    }

    @Override
    public LiveData<List<Team>> getGameEligibleTeams(int[] teams){
        return mTeamDao.getGameEligibleTeams(teams);
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

}
