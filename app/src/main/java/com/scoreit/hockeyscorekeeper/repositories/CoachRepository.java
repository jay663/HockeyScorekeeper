package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.model.Coach;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface CoachRepository {
    LiveData<List<Coach>> getAllCoaches();

    LiveData<List<Coach>> getTeamCoaches(int teamId);

    LiveData<Coach> getCoach(int teamId, int coachId);

    void addCoach(Coach coach);

    void removeCoach(Coach coach);

    void updateCoach(Coach coach);
}
