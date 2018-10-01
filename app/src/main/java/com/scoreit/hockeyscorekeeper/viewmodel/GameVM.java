package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.model.GameShots;
import com.scoreit.hockeyscorekeeper.repositories.GameRepository;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepository;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepositoryImpl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GameVM extends ViewModel {
    private GameRepository mRepository;
    public LiveData<Game> game;
    public LiveData<GameShots> shots;

    public GameVM(long id, Application application) {
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        GameShotsDao shotsDao = db.getGameShotsDao();
        GameScoringDao scoringDao = db.getGameScoringDao();
        GameDao gameDao = db.getGameDao();

        mRepository = new GameRepository(application);
        game = mRepository.getGame(id);
        shots = mRepository.getGameShots(id);

    }

    public void addAwayTeamShot() {
        int curentPeriod = game.getValue().currentPeriod;
        GameShots gameShots = shots.getValue();
        addAwayTeamShot(curentPeriod, gameShots);
    }

    public void addHomeTeamShot() {
        int curentPeriod = game.getValue().currentPeriod;
        GameShots gameShots = shots.getValue();
        addHomeTeamShot(curentPeriod, gameShots);
    }

    public void homeTeamScored(GameScoring scoring) {
        Game hockeyGame = game.getValue();
        addHomeTeamGoal(hockeyGame, scoring);
    }

    public void awayTeamScored(GameScoring scoring) {
        Game hockeyGame = game.getValue();
        addAwayTeamGoal(hockeyGame, scoring);
    }

    public void addPeriod() {
        Game hockeyGame = game.getValue();
        hockeyGame.currentPeriod++;
        mRepository.updateGame(hockeyGame);
    }

    public boolean isGameOver() {
        Game hockeyGame = game.getValue();
        // Game Finishes after 3 periods and there is no tie
        return (hockeyGame.currentPeriod >= 3 &&
                (hockeyGame.scoreboard.homeFinalScore != hockeyGame.scoreboard.awayFinalScore));
    }

    private void addHomeTeamGoal(Game hockeyGame, GameScoring scoring) {
        switch (hockeyGame.currentPeriod) {
            case 1:
                hockeyGame.scoreboard.homePeriod1Goals++;
                break;
            case 2:
                hockeyGame.scoreboard.homePeriod2Goals++;
                break;
            case 3:
                hockeyGame.scoreboard.homePeriod3Goals++;
                break;
            case 4:
                hockeyGame.scoreboard.homeOTGoals++;
                break;
            default:
        }

        hockeyGame.scoreboard.homeFinalScore++;
        mRepository.addGameScoring(scoring);
        mRepository.updateGame(hockeyGame);
    }

    private void addAwayTeamGoal(Game hockeyGame, GameScoring scoring) {
        switch (hockeyGame.currentPeriod) {
            case 1:
                hockeyGame.scoreboard.awayPeriod1Goals++;
                break;
            case 2:
                hockeyGame.scoreboard.awayPeriod2Goals++;
                break;
            case 3:
                hockeyGame.scoreboard.awayPeriod3Goals++;
                break;
            case 4:
                hockeyGame.scoreboard.awayOTGoals++;
                break;
            default:
        }

        hockeyGame.scoreboard.awayFinalScore++;
        mRepository.addGameScoring(scoring);
        mRepository.updateGame(hockeyGame);
    }

    private void addAwayTeamShot(int curentPeriod, GameShots gameShots) {
        switch (curentPeriod) {
            case 1:
                gameShots.awayPeriod1Shots++;
                break;
            case 2:
                gameShots.awayPeriod2Shots++;
                break;
            case 3:
                gameShots.awayPeriod3Shots++;
                break;
            case 4:
                gameShots.awayOTShots++;
                break;
            default:
        }

        gameShots.awayShotTotal++;
        mRepository.updateGameShots(gameShots);
    }

    private void addHomeTeamShot(int curentPeriod, GameShots gameShots) {
        switch (curentPeriod) {
            case 1:
                gameShots.homePeriod1Shots++;
                break;
            case 2:
                gameShots.homePeriod2Shots++;
                break;
            case 3:
                gameShots.homePeriod3Shots++;
                break;
            case 4:
                gameShots.homeOTShots++;
                break;
            default:
        }

        gameShots.homeShotTotal++;
        mRepository.updateGameShots(gameShots);
    }


}
