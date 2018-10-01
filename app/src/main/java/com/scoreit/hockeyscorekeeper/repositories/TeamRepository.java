package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.IAsyncTaskResults;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public interface TeamRepository {

    void addOnClickListener(IAsyncTaskResults<Long> delegate);

    void removeOnClickListener();

    LiveData<List<Team>> getAllTeams();

    LiveData<Team> getTeam (int teamId);

    void insert (Team team);

    Single<Long> addTeam (final Team team);

    void removeTeam(final Team team);

    void updateTeam(final Team team);

    LiveData<List<Team>> getGameEligibleTeams(int[] teams);
}
