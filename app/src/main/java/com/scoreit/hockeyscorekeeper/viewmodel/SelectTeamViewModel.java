package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;

public class SelectTeamViewModel extends AndroidViewModel {
    private HockeyRepository mRepository;
    private List<Team> mAllTeams;
    Team[] teams = {new Team("San Jose Sharks", "Sharks", "San Jose"), new Team("Columbus Blue Jackets", "Blue Jackets", "Columbus"),
            new Team("Winnipeg Jets", "Jets", "Winnipeg"), new Team("Nashville Grapes", "Grapes", "Nashville")};


    public SelectTeamViewModel(Application application) {
        super(application);
        mRepository = new HockeyRepository(application);
        for (int i = 0; i < teams.length; i++){
            teams[i].mTeamId = i + 1;
        }

        mAllTeams = Arrays.asList(teams);
    }

    public List<Team> getAllTeams() {
        return mAllTeams;
    }

    public void addTeam(Team team){
        Team t = mAllTeams.get(mAllTeams.size() - 1);
        team.mTeamId = t.mTeamId + 1;
        mAllTeams.add(team);
    }
}
