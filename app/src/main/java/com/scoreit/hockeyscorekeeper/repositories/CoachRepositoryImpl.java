package com.scoreit.hockeyscorekeeper.repositories;

import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.CoachDao;
import com.scoreit.hockeyscorekeeper.model.Coach;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CoachRepositoryImpl implements CoachRepository {
    private CoachDao mCoachDao;

    public CoachRepositoryImpl(CoachDao dao) {
        mCoachDao = dao;
    }

    @Override
    public LiveData<List<Coach>> getAllCoaches() {
        return mCoachDao.getAll();
    }

    @Override
    public LiveData<List<Coach>> getTeamCoaches(int teamId) {
        return mCoachDao.getCoachesByTeam(teamId);
    }

    @Override
    public LiveData<Coach> getCoach(int teamId, int coachId) {
        return mCoachDao.getCoach(teamId, coachId);
    }

    @Override
    public void addCoach(Coach coach) {
        new addAsyncCoach(mCoachDao, coach).execute();
    }

    @Override
    public void removeCoach(Coach coach) {
        new removeAsyncCoach(mCoachDao, coach).execute();
    }

    @Override
    public void updateCoach(Coach coach) {
        new updateAsyncCoach(mCoachDao, coach).execute();
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

}
