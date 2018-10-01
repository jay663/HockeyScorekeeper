package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface EligibleTeamsRepository {
    public LiveData<List<Team>> getGameReadyTeams();
}
