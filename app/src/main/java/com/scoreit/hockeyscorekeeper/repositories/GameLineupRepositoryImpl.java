package com.scoreit.hockeyscorekeeper.repositories;

import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.GameLineupDao;
import com.scoreit.hockeyscorekeeper.model.GameLineup;
import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class GameLineupRepositoryImpl implements GameLineupRepository {
    private GameLineupDao mGameLineupDao;

    public GameLineupRepositoryImpl(GameLineupDao gameLineupDao) {
        this.mGameLineupDao = gameLineupDao;
    }

    @Override
    public void addGameLineup(ArrayList lineup) {
        new addAsyncGameLineup(mGameLineupDao, lineup).execute();
    }

    @Override
    public LiveData<List<Player>> getActivePlayers(long gameId, int teamId, String homeOrAway) {
        return mGameLineupDao.getActivePlayers(gameId, teamId, homeOrAway);
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
}
