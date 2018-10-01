package com.scoreit.hockeyscorekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public Game(int homeTeamId, int awayTeamId, String awayTeam, String homeTeam, String gameDate, String arena) {
        this.homeTeamId = homeTeamId;
        this.homeTeam = homeTeam;
        this.arena = arena;
        this.awayTeamId = awayTeamId;
        this.awayTeam = awayTeam;
        this.gameDate = gameDate;
        this.scoreboard = new Scoreboard();
        currentPeriod = 1;
    }
}






