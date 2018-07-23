package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "game_table")
public class Game {
    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }

    public int getHomePeriod1Goals() {
        return homePeriod1Goals;
    }

    public void setHomePeriod1Goals(int homePeriod1Goals) {
        this.homePeriod1Goals = homePeriod1Goals;
    }

    public int getHomePeriod2Goals() {
        return homePeriod2Goals;
    }

    public void setHomePeriod2Goals(int homePeriod2Goals) {
        this.homePeriod2Goals = homePeriod2Goals;
    }

    public int getHomePeriod3Goals() {
        return homePeriod3Goals;
    }

    public void setHomePeriod3Goals(int homePeriod3Goals) {
        this.homePeriod3Goals = homePeriod3Goals;
    }

    public int getHomeOTGoals() {
        return homeOTGoals;
    }

    public void setHomeOTGoals(int homeOTGoals) {
        this.homeOTGoals = homeOTGoals;
    }

    public int getHomeFinalScore() {
        return homeFinalScore;
    }

    public void setHomeFinalScore(int homeFinalScore) {
        this.homeFinalScore = homeFinalScore;
    }

    public int getAwayPeriod1Goals() {
        return awayPeriod1Goals;
    }

    public void setAwayPeriod1Goals(int awayPeriod1Goals) {
        this.awayPeriod1Goals = awayPeriod1Goals;
    }

    public int getAwayPeriod2Goals() {
        return awayPeriod2Goals;
    }

    public void setAwayPeriod2Goals(int awayPeriod2Goals) {
        this.awayPeriod2Goals = awayPeriod2Goals;
    }

    public int getAwayPeriod3Goals() {
        return awayPeriod3Goals;
    }

    public void setAwayPeriod3Goals(int awayPeriod3Goals) {
        this.awayPeriod3Goals = awayPeriod3Goals;
    }

    public int getAwayOTGoals() {
        return awayOTGoals;
    }

    public void setAwayOTGoals(int awayOTGoals) {
        this.awayOTGoals = awayOTGoals;
    }

    public int getAwayFinalScore() {
        return awayFinalScore;
    }

    public void setAwayFinalScore(int awayFinalScore) {
        this.awayFinalScore = awayFinalScore;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "homeTeamId")
    private int homeTeamId;

    @ColumnInfo(name = "homeTeam")
    private String homeTeam;

    @ColumnInfo(name = "awayTeamId")
    private int awayTeamId;

    @ColumnInfo(name = "awayTeam")
    private String awayTeam;

    @ColumnInfo(name = "gameDateTime")
    private String gameDate;

    @ColumnInfo(name = "arena")
    private String arena;

    @ColumnInfo(name = "homePeriod1Goals")
    private int homePeriod1Goals;

    @ColumnInfo(name = "homePeriod2Goals")
    private int homePeriod2Goals;

    @ColumnInfo(name = "homePeriod3Goals")
    private int homePeriod3Goals;

    @ColumnInfo(name = "homeOTGoals")
    private int homeOTGoals;

    @ColumnInfo(name = "homeFinalScore")
    private int homeFinalScore;

    @ColumnInfo(name = "awayPeriod1Goals")
    private int awayPeriod1Goals;

    @ColumnInfo(name = "awayPeriod2Goals")
    private int awayPeriod2Goals;

    @ColumnInfo(name = "awayPeriod3Goals")
    private int awayPeriod3Goals;

    @ColumnInfo(name = "awayOTGoals")
    private int awayOTGoals;

    @ColumnInfo(name = "awayfinalScore")
    private int awayFinalScore;

    public Game(int homeTeamId, int awayTeamId, String gameDate, String arena) {
        this.homeTeamId = homeTeamId;
        this.arena = arena;
        this.awayTeamId = awayTeamId;
        this.gameDate = gameDate;
        this.awayFinalScore = 0;
        this.awayOTGoals = 0;
        this.awayPeriod1Goals = 0;
        this.awayPeriod2Goals = 0;
        this.awayPeriod3Goals = 0;
        this.homeFinalScore = 0;
        this.homeOTGoals = 0;
        this.homePeriod1Goals = 0;
        this.homePeriod2Goals = 0;
        this.homePeriod3Goals = 0;
    }

    //public String getWord(){return this.mWord;}

}






