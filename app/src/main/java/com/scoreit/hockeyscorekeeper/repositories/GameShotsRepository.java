package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.model.GameShots;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public interface GameShotsRepository {
    LiveData<GameShots> getGameShots(long id);

    void updateGameShots(GameShots gameShots);

    Single<Boolean> addGameShots(GameShots gameShots);
}
