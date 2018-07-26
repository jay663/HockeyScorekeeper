package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.IAsyncTaskResults;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public class TeamViewModel extends AndroidViewModel {

    private HockeyRepository mRepository;
    private LiveData<List<Team>> mAllTeams;

    public TeamViewModel (Application application) {
        super(application);
        mRepository = new HockeyRepository(application);
        mAllTeams = mRepository.getAllTeams();
    }

    public LiveData<List<Team>> getAllTeams() { return mAllTeams; }

    public LiveData<Team> getTeam(int teamId){
        return mRepository.getTeam(teamId);
    }

    public void insert(Team team, IAsyncTaskResults<Long> addTeamListener) {
        mRepository.addTeamDelegate = addTeamListener;
        mRepository.insert(team);
    }

    public void update(Team team) {
        mRepository.updateTeam(team);
    }

    public Single<Long> addTeam(Team team) {
        return mRepository.addTeam(team);
    }

    public void addPlayer(Player player) { mRepository.addPlayer(player); }

    public void deleteTeam(Team team) { mRepository.removeTeam(team);}

    public void removeListener(){
        mRepository.addTeamDelegate = null;
    }
}
