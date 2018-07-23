package com.scoreit.hockeyscorekeeper.viewmodel;

import com.scoreit.hockeyscorekeeper.BR;
import com.scoreit.hockeyscorekeeper.model.Team;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class GameVM extends BaseObservable {
    private int homePeriod1Goals;
    private int homePeriod2Goals;
    private int homePeriod3Goals;
    private int homeOTGoals;
    private int homeTotal;
    private int awayPeriod1Goals;
    private int awayPeriod2Goals;
    private int awayPeriod3Goals;
    private int awayOTGoals;
    private int awayTotal;

    private int homePeriod1Shots;
    private int homePeriod2Shots;
    private int homePeriod3Shots;
    private int homeOTShots;
    private int homeShotTotal;
    private int awayPeriod1Shots;
    private int awayPeriod2Shots;
    private int awayPeriod3Shots;
    private int awayOTShots;
    private int awayShotTotal;
    private int currentPeriod;

    @Bindable
    public int getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(int currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    @Bindable
    public int getHomePeriod1Shots() {
        return homePeriod1Shots;
    }

    public void setHomePeriod1Shots(int homePeriod1Shots) {
        this.homePeriod1Shots = homePeriod1Shots;
        notifyPropertyChanged(BR.homePeriod1Shots);
    }

    @Bindable
    public int getHomePeriod2Shots() {
        return homePeriod2Shots;
    }

    public void setHomePeriod2Shots(int homePeriod2Shots) {
        this.homePeriod2Shots = homePeriod2Shots;
        notifyPropertyChanged(BR.homePeriod2Shots);
    }

    @Bindable
    public int getHomePeriod3Shots() {
        return homePeriod3Shots;
    }

    public void setHomePeriod3Shots(int homePeriod3Shots) {
        this.homePeriod3Shots = homePeriod3Shots;
        notifyPropertyChanged(BR.homePeriod2Shots);
    }

    @Bindable
    public int getHomeOTShots() {
        return homeOTShots;
    }

    public void setHomeOTShots(int homeOTShots) {
        this.homeOTShots = homeOTShots;
        notifyPropertyChanged(BR.homeOTShots);
    }

    @Bindable
    public int getHomeShotTotal() {
        return homeShotTotal;
    }

    public void setHomeShotTotal(int homeShotTotal) {
        this.homeShotTotal = homeShotTotal;
        notifyPropertyChanged(BR.homeShotTotal);
    }

    @Bindable
    public int getAwayPeriod1Shots() {
        return awayPeriod1Shots;
    }

    public void setAwayPeriod1Shots(int awayPeriod1Shots) {
        this.awayPeriod1Shots = awayPeriod1Shots;
        notifyPropertyChanged(BR.awayPeriod1Shots);
    }

    @Bindable
    public int getAwayPeriod2Shots() {
        return awayPeriod2Shots;
    }

    public void setAwayPeriod2Shots(int awayPeriod2Shots) {
        this.awayPeriod2Shots = awayPeriod2Shots;
        notifyPropertyChanged(BR.awayPeriod2Shots);
    }

    @Bindable
    public int getAwayPeriod3Shots() {
        return awayPeriod3Shots;
    }

    public void setAwayPeriod3Shots(int awayPeriod3Shots) {
        this.awayPeriod3Shots = awayPeriod3Shots;
        notifyPropertyChanged(BR.awayPeriod3Shots);
    }

    @Bindable
    public int getAwayOTShots() {
        return awayOTShots;
    }

    public void setAwayOTShots(int awayOTShots) {
        this.awayOTShots = awayOTShots;
        notifyPropertyChanged(BR.awayOTShots);
    }

    @Bindable
    public int getAwayShotTotal() {
        return awayShotTotal;
    }

    public void setAwayShotTotal(int awayShotTotal) {
        this.awayShotTotal = awayShotTotal;
        notifyPropertyChanged(BR.awayShotTotal);
    }

    private String homeTeamName;
    private String awayTeamName;

    private int homeTeamId;
    private int awayTeamId;
    Team[] teams = {new Team("San Jose Sharks", "Sharks", "San Jose"), new Team("Columbus Blue Jackets", "Blue Jackets", "Columbus"),
            new Team("Winnipeg Jets", "Jets", "Winnipeg"), new Team("Nashville Grapes", "Grapes", "Nashville")};

    @Bindable
    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    @Bindable
    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    @Bindable
    public int getHomePeriod2Goals() {
        return homePeriod2Goals;
    }

    public void setHomePeriod2Goals(int homePeriod2Goals) {
        this.homePeriod2Goals = homePeriod2Goals;
        notifyPropertyChanged(BR.homePeriod2Goals);
    }

    @Bindable
    public int getHomePeriod3Goals() {
        return homePeriod3Goals;
    }

    @Bindable
    public void setHomePeriod3Goals(int homePeriod3Goals) {
        this.homePeriod3Goals = homePeriod3Goals;
        notifyPropertyChanged(BR.homePeriod3Goals);
    }

   @Bindable
    public int getHomeTotal() {
        return homeTotal;
    }

    public void setHomeTotal(int homeTotal) {
        this.homeTotal = homeTotal;
        notifyPropertyChanged(BR.homeTotal);
    }

   @Bindable
    public int getAwayPeriod1Goals() {
        return awayPeriod1Goals;
    }

    public void setAwayPeriod1Goals(int awayPeriod1Goals) {
        this.awayPeriod1Goals = awayPeriod1Goals;
        notifyPropertyChanged(BR.awayPeriod1Goals);
    }

   @Bindable
    public int getAwayPeriod2Goals() {
        return awayPeriod2Goals;
    }

    public void setAwayPeriod2Goals(int awayPeriod2Goals) {
        this.awayPeriod2Goals = awayPeriod2Goals;
        notifyPropertyChanged(BR.awayPeriod2Goals);
    }

   @Bindable
    public int getAwayPeriod3Goals() {
        return awayPeriod3Goals;
    }

    public void setAwayPeriod3Goals(int awayPeriod3Goals) {
        this.awayPeriod3Goals = awayPeriod3Goals;
        notifyPropertyChanged(BR.awayPeriod3Goals);
    }

   @Bindable
    public int getAwayTotal() {
        return awayTotal;
    }

    public void setAwayTotal(int awayTotal) {
        this.awayTotal = awayTotal;
        notifyPropertyChanged(BR.awayTotal);
    }

   @Bindable
    public int getHomePeriod1Goals() {
        return homePeriod1Goals;
    }

    public void setHomePeriod1Goals(int homePeriod1Goals) {
        this.homePeriod1Goals = homePeriod1Goals;
        notifyPropertyChanged(BR.homePeriod1Goals);
    }

   @Bindable
    public int getHomeOTGoals() {
        return homeOTGoals;
    }

    public void setHomeOTGoals(int homeOTGoals) {
        this.homeOTGoals = homeOTGoals;
    }

   @Bindable
    public int getAwayOTGoals() {
        return awayOTGoals;
    }

    public void setAwayOTGoals(int awayOTGoals) {
        this.awayOTGoals = awayOTGoals;
    }

    public GameVM() {
        this.homePeriod1Goals = 0;
        this.homePeriod2Goals = 0;
        this.homePeriod3Goals = 0;
        this.homeOTGoals = 0;
        this.homeTotal = 0;
        this.awayPeriod1Goals = 0;
        this.awayPeriod2Goals = 0;
        this.awayPeriod3Goals = 0;
        this.awayOTGoals = 0;
        this.awayTotal = 0;

        this.homePeriod1Shots = 0;
        this.homePeriod2Shots = 0;
        this.homePeriod3Shots = 0;
        this.homeOTShots     = 0;
        this.homeShotTotal   = 0;
        this.awayPeriod1Shots = 0;
        this.awayPeriod2Shots = 0;
        this.awayPeriod3Shots = 0;
        this.awayOTShots = 0;
        this.awayShotTotal = 0;

        this.currentPeriod = 1;
        this.homeTeamId = 1;
        this.awayTeamId = 1;
        this.homeTeamName = "Monsters";
        this.awayTeamName = "Boaters";
    }


    public GameVM(int homeTeamId, int awayTeamId) {
        this.homePeriod1Goals = 0;
        this.homePeriod2Goals = 0;
        this.homePeriod3Goals = 0;
        this.homeOTGoals = 0;
        this.homeTotal = 0;
        this.awayPeriod1Goals = 0;
        this.awayPeriod2Goals = 0;
        this.awayPeriod3Goals = 0;
        this.awayOTGoals = 0;
        this.awayTotal = 0;

        this.homePeriod1Shots = 0;
        this.homePeriod2Shots = 0;
        this.homePeriod3Shots = 0;
        this.homeOTShots     = 0;
        this.homeShotTotal   = 0;
        this.awayPeriod1Shots = 0;
        this.awayPeriod2Shots = 0;
        this.awayPeriod3Shots = 0;
        this.awayOTShots = 0;
        this.awayShotTotal = 0;
        this.currentPeriod = 1;

        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamName = teams[homeTeamId].mMascott;
        this.awayTeamName = teams[awayTeamId].mMascott;
    }
}
