package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SelectTeamViewModel extends AndroidViewModel {
    private HockeyRepository mRepository;
    private LiveData<List<Team>> mAllTeams;
    private Team mHomeTeam;
    private Team mAwayTeam;

    Team[] teams = {
            new Team("Lakers", "Cleveland", "Lakers"),
            new Team("Seagulls", "Baltimore", "Seagulls"),
            new Team("Nashville Grapes", "Grapes", "Nashville")};


    public Team getHomeTeam() {
        return mHomeTeam;
    }

    public void setHomeTeam(Team mHomeTeam) {
        this.mHomeTeam = mHomeTeam;
    }

    public Team getAwayTeam() {
        return mAwayTeam;
    }

    public void setAwayTeam(Team mAwayTeam) {
        this.mAwayTeam = mAwayTeam;
    }

    public SelectTeamViewModel(Application application) {
        super(application);
        mAwayTeam = null;
        mHomeTeam = null;

        mRepository = new HockeyRepository(application);
    }

    public LiveData<List<Team>> getAllTeams() {
        mAllTeams = mRepository.getGameReadyTeams();
        return mAllTeams;
    }

}
