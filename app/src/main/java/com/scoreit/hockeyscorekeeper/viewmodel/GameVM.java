package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameShots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class GameVM extends ViewModel {
    private HockeyRepository mRepository;
    public LiveData<Game> game;
    public LiveData<GameShots> shots;

    public GameVM(long id, Application application) {
        mRepository = new HockeyRepository(application);
        game = mRepository.getGame(id);
        shots = mRepository.getGameShots(id);

    }

    public void addAwayTeamShot() {
        int curentPeriod = game.getValue().currentPeriod;
        shots.getValue().addAwayTeamShot(curentPeriod);
        mRepository.updateGameShots(shots.getValue());

    }

    public void addHomeTeamShot() {
        int curentPeriod = game.getValue().currentPeriod;
        shots.getValue().addHomeTeamShot(curentPeriod);
        mRepository.updateGameShots(shots.getValue());

    }

    public void homeTeamScored() {
        game.getValue().addHomeTeamGoal();
        addHomeTeamShot();

        mRepository.updateGame(game.getValue());
    }

    public void awayTeamScored() {
        game.getValue().addAwayTeamGoal();
        addAwayTeamShot();

        mRepository.updateGame(game.getValue());
    }

    public boolean isGameFinished(){
        return game.getValue().isGameFinished();
    }
}
