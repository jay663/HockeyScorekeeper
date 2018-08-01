package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.model.Coach;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CoachViewModel  extends AndroidViewModel {

    private HockeyRepository mRepository;
    private LiveData<List<Coach>> mCoaches;

    public CoachViewModel(Application application) {
        super(application);
        mRepository = new HockeyRepository(application);
        mCoaches = mRepository.getAllCoaches();
    }

    public LiveData<List<Coach>> getTeamCoaches(int teamId) { return mRepository.getTeamCoaches(teamId); }

    public void insert(Coach coach) { mRepository.addCoach(coach); }


    public void deleteCoach(Coach coach) {
        mRepository.removeCoach(coach);
    }

    public LiveData<Coach> getCoach(int teamId, int coachId) {
        return mRepository.getCoach(teamId, coachId);
    }

    public void update(Coach coach) {
        mRepository.updateCoach(coach);
    }
}
