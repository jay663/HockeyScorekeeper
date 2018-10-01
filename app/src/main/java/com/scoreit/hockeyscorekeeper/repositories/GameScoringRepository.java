package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.model.GameScoring;

import io.reactivex.Single;

public interface GameScoringRepository {
    Single<Long> addGameScoring(GameScoring goal);
}
