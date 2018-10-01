package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.RosterCountDao;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.repositories.EligibleTeamsRepository;
import com.scoreit.hockeyscorekeeper.repositories.EligibleTeamsRepositoryImpl;
import com.scoreit.hockeyscorekeeper.repositories.GameRepository;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepository;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepositoryImpl;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public class SelectTeamViewModel extends AndroidViewModel {
    private EligibleTeamsRepository mRepository;
    private LiveData<List<Team>> mAllTeams;
    private GameRepository mGameRepository;

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
        RosterCountDao rosterDao = HockeyDatabase.getDatabase(application).getRosterCountDao();
        TeamDao teamDao = HockeyDatabase.getDatabase(application).getTeamDao();
        TeamRepository teamRepository = new TeamRepositoryImpl(teamDao);
        mRepository = new EligibleTeamsRepositoryImpl(teamRepository, rosterDao);
        mGameRepository = new GameRepository(application);


        mAwayTeam = null;
        mHomeTeam = null;

    }

    public LiveData<List<Team>> getAllTeams() {
        mAllTeams = mRepository.getGameReadyTeams();
        return mAllTeams;
    }

    public Single<Long> createGame(int homeTeamId, String homeTeam, int awayTeamId, String awayTeam, String arena) {
        Date gameDate = new Date();
        Game game = new Game(homeTeamId, awayTeamId, awayTeam, homeTeam, gameDate.toString(), arena);
        return mGameRepository.addGame(game);
    }

}
