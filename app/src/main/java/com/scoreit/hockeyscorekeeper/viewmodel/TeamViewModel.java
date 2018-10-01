package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.IAsyncTaskResults;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepository;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepositoryImpl;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public class TeamViewModel extends AndroidViewModel {

    private TeamRepository mRepository;

    public TeamViewModel (Application application) {
        super(application);
        TeamDao dao = HockeyDatabase.getDatabase(application).getTeamDao();
        mRepository = new TeamRepositoryImpl(dao);
    }

    public LiveData<List<Team>> getAllTeams() { return mRepository.getAllTeams(); }

    public LiveData<Team> getTeam(int teamId){
        return mRepository.getTeam(teamId);
    }

    public void insert(Team team, IAsyncTaskResults<Long> addTeamListener) {
        mRepository.addOnClickListener(addTeamListener);
        mRepository.insert(team);
    }

    public void update(Team team) {
        mRepository.updateTeam(team);
    }

    public Single<Long> addTeam(Team team) {
        return mRepository.addTeam(team);
    }

    public void deleteTeam(Team team) { mRepository.removeTeam(team);}

    public void removeListener(){
        mRepository.removeOnClickListener();
    }
}
