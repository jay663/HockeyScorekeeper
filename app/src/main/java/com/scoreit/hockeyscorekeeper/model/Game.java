package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "game_table")
public class Game {
    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
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

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;

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

    @Embedded
    public Scoreboard scoreboard;

    @ColumnInfo(name = "currentPeriod")
    public int currentPeriod;

    public Game(int homeTeamId, int awayTeamId, String gameDate, String arena) {
        this.homeTeamId = homeTeamId;
        this.arena = arena;
        this.awayTeamId = awayTeamId;
        this.gameDate = gameDate;
    }

    @Ignore
    public void addHomeTeamGoal() {
        switch (currentPeriod) {
            case 1:
                scoreboard.homePeriod1Goals++;
                break;
            case 2:
                scoreboard.homePeriod2Goals++;
                break;
            case 3:
                scoreboard.homePeriod3Goals++;
                break;
            case 4:
                scoreboard.homeOTGoals++;
                break;
            default:
        }

        scoreboard.homeFinalScore++;
    }

    @Ignore
    public void addAwayTeamGoal() {
        switch (currentPeriod) {
            case 1:
                scoreboard.awayPeriod1Goals++;
                break;
            case 2:
                scoreboard.awayPeriod2Goals++;
                break;
            case 3:
                scoreboard.awayPeriod3Goals++;
                break;
            case 4:
                scoreboard.awayOTGoals++;
                break;
            default:
        }

        scoreboard.awayFinalScore++;
    }

    @Ignore
    public boolean isGameFinished() {
        // Game Finishes after 3 periods and there is no tie
        return (currentPeriod >= 3 && (scoreboard.homeFinalScore != scoreboard.awayFinalScore));
    }
}






