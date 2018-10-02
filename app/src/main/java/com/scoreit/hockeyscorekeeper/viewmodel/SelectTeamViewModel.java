package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
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
    private GameRepository mGameRepository;

    private Team mHomeTeam;
    private Team mAwayTeam;


    public SelectTeamViewModel(Application application) {
        super(application);
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        RosterCountDao rosterDao = db.getRosterCountDao();
        TeamDao teamDao = db.getTeamDao();
        GameDao gameDao = db.getGameDao();
        GameScoringDao gameScoringDao = db.getGameScoringDao();
        GameShotsDao gameShotDao = db.getGameShotsDao();

        TeamRepository teamRepository = new TeamRepositoryImpl(teamDao);
        mRepository = new EligibleTeamsRepositoryImpl(teamRepository, rosterDao);
        mGameRepository = new GameRepository(gameDao, gameScoringDao, gameShotDao);


        mAwayTeam = null;
        mHomeTeam = null;

    }

    public LiveData<List<Team>> getAllTeams() {
        LiveData<List<Team>> mAllTeams = mRepository.getGameReadyTeams();
        return mAllTeams;
    }

    public Single<Long> createGame(int homeTeamId, String homeTeam, int awayTeamId, String awayTeam, String arena) {
        Date gameDate = new Date();
        Game game = new Game(homeTeamId, awayTeamId, awayTeam, homeTeam, gameDate.toString(), arena);
        return mGameRepository.addGame(game);
    }

}
