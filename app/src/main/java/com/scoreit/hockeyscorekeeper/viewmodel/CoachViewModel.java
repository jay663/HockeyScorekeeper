package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.data.CoachDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.model.Coach;
import com.scoreit.hockeyscorekeeper.repositories.CoachRepository;
import com.scoreit.hockeyscorekeeper.repositories.CoachRepositoryImpl;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CoachViewModel  extends AndroidViewModel {

    private CoachRepository mRepository;
    private LiveData<List<Coach>> mCoaches;

    public CoachViewModel(Application application) {
        super(application);
        CoachDao dao = HockeyDatabase.getDatabase(application).getCoachDao();
        mRepository = new CoachRepositoryImpl(dao);
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
