package com.scoreit.hockeyscorekeeper.repositories;

import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.model.GameShots;

import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GameRepository {
    private GameDao mGameDao;
    private GameShotsRepository mGameShotsRepository;
    private GameScoringRepository mGameScoringRepository;

    public GameRepository(GameDao gameDao, GameScoringDao scoringDao, GameShotsDao shotsDao) {
        mGameDao = gameDao;

        mGameShotsRepository = new GameShotsRepositoryImpl(shotsDao);
        mGameScoringRepository = new GameScoringRepositoryImpl(scoringDao);

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
        return mGameShotsRepository.getGameShots(id);
    }

    public void updateGameShots(GameShots gameShots) {
        mGameShotsRepository.updateGameShots(gameShots);
    }

    public void addGoal(Game hockeyGame, GameScoring scoring) {
        mGameScoringRepository.addGameScoring(scoring);
        updateGame(hockeyGame);
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

}
