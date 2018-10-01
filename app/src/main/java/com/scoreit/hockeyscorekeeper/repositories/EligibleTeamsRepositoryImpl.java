package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.data.RosterCountDao;
import com.scoreit.hockeyscorekeeper.model.RosterCount;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class EligibleTeamsRepositoryImpl implements EligibleTeamsRepository {
    private TeamRepository mTeamRepository;
    private RosterCountDao mRosterCountDao;

    public EligibleTeamsRepositoryImpl(TeamRepository teamRepository, RosterCountDao dao) {
        mTeamRepository = teamRepository;
        mRosterCountDao = dao;
    }

    @Override
    public LiveData<List<Team>> getGameReadyTeams() {
        LiveData<List<RosterCount>> eligibleRosters = mRosterCountDao.eligibleTeamIds(5);

        LiveData<List<Team>> teams = Transformations.switchMap(eligibleRosters,
                new Function<List<RosterCount>, LiveData<List<Team>>>() {
                    @Override
                    public LiveData<List<Team>> apply(List<RosterCount> input) {
                        int[] teamIds = new int[input.size()];

                        for (int i=0; i<input.size();i++){
                            teamIds[i] = input.get(i).teamId;
                        }

                        return mTeamRepository.getGameEligibleTeams(teamIds);
                    }
                });

        return teams;
    }
}
