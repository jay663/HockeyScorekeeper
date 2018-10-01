package com.scoreit.hockeyscorekeeper.repositories;

import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.PlayerDao;
import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PlayerRepositoryImpl implements PlayerRepository {
    private PlayerDao mPlayerDao;

    public PlayerRepositoryImpl(PlayerDao dao) {
        mPlayerDao = dao;
    }

    @Override
    public LiveData<List<Player>> getAllPlayers() {
        return mPlayerDao.getAll();
    }

    @Override
    public LiveData<Player> getPlayer(int teamId, int playerId) {
        return mPlayerDao.getPlayer(teamId, playerId);
    }

    @Override
    public LiveData<List<Player>> getTeamPlayers(int teamId) {
        return mPlayerDao.getPlayersByTeam(teamId);
    }

    @Override
    public void addPlayer(Player player) {
        new addAsyncPlayer(mPlayerDao, player).execute();
    }

    @Override
    public void removePlayer(Player player) {
        new removeAsyncPlayer(mPlayerDao, player).execute();
    }

    @Override
    public void updatePlayer(Player player) {
        new updateAsyncPlayer(mPlayerDao, player).execute();
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

}
