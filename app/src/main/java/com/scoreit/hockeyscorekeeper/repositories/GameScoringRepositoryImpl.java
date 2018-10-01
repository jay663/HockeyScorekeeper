package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.model.GameScoring;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GameScoringRepositoryImpl implements GameScoringRepository {
    private GameScoringDao mGameScoringDao;

    public GameScoringRepositoryImpl(GameScoringDao gameScoringDao) {
        this.mGameScoringDao = gameScoringDao;
    }

    @Override
    public Single<Long> addGameScoring(GameScoring goal) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long gameId = mGameScoringDao.insertAll(goal);
                return new Long(gameId);
            }
        }).subscribeOn(Schedulers.io());
    }
}
