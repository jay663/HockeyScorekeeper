package com.scoreit.hockeyscorekeeper.repositories;

import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.model.GameShots;

import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GameShotsRepositoryImpl implements GameShotsRepository {
    private GameShotsDao mGameShotsDao;

    public GameShotsRepositoryImpl(GameShotsDao mGameShotsDao) {
        this.mGameShotsDao = mGameShotsDao;
    }

    @Override
    public LiveData<GameShots> getGameShots(long id) {
        return mGameShotsDao.getGameShots(id);
    }

    @Override
    public void updateGameShots(GameShots gameShots) {
        new updateAsyncGameShots(mGameShotsDao, gameShots).execute();
    }

    @Override
    public Single<Boolean> addGameShots(GameShots gameShots) {
        return Single.fromCallable(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                mGameShotsDao.insert(gameShots);
                return true;
            }
        }).subscribeOn(Schedulers.io());
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
