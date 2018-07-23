package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.annotation.NonNull;

//@Entity(tableName = "player_table",
//        foreignKeys = @ForeignKey(
//        entity = Team.class,
//        parentColumns = "teamId",
//        childColumns = "jerseyNumber"))

@Entity(tableName = "player_table",
        primaryKeys = {"jerseyNumber", "teamId"})
public class Player {
    @NonNull
    @ColumnInfo(name = "jerseyNumber")
    public int mJerseyNumber;

    @NonNull
    @ColumnInfo(name = "teamId")
    public int mTeamId;

    @ColumnInfo(name = "playerName")
    public String mPlayerName;

    @ColumnInfo(name = "position")
    public String mPosition;

    @ColumnInfo(name = "gp")
    public int mGamesPlayed;

    @ColumnInfo(name = "goals")
    public int mGoals;

    @ColumnInfo(name = "assists")
    public int mAssists;

    @ColumnInfo(name = "plusMinus")
    public int mPlusMinus;

    @ColumnInfo(name = "penaltyMinutes")
    public String mPenaltyMinutes;

    @ColumnInfo(name = "ppg")
    public int mPowerPlayGame;

    @ColumnInfo(name = "shg")
    public int mShortHandedGoals;

    @ColumnInfo(name = "pointsPerGame")
    public double mPointsPerGame;

    @ColumnInfo(name = "pimpg")
    public double mPenaltyMinutesPerGame;

    @ColumnInfo(name = "sh")
    public int mShots;

    @ColumnInfo(name = "sog")
    public int mShotoutGoals;

    @ColumnInfo(name = "soa")
    public int mShootoutAttempts;

    @ColumnInfo(name = "sogw")
    public int mShootoutWinningGoals;

    @ColumnInfo(name = "shootoutpercent")
    public float mShootoutPercentage;

    @ColumnInfo(name = "min")
    public String mMinutesPlayed;

    @ColumnInfo(name = "ga")
    public int mGoalsAgainst;

    @ColumnInfo(name = "so")
    public int mShutouts;

    @ColumnInfo(name = "gaa")
    public double mGoalsAgainstAvg;

    @ColumnInfo(name = "w")
    public int mWins;

    @ColumnInfo(name = "l")
    public int mLosses;

    @ColumnInfo(name = "ot")
    public int mOvertime;

    @ColumnInfo(name = "svs")
    public int mSaves;

    @ColumnInfo(name = "shotsagainst")
    public int mShotsAgainst;

    @ColumnInfo(name = "savp")
    public float mSavePercentage;

    @ColumnInfo(name = "sris")
    public double mShotsReceivedInShootout;

    @ColumnInfo(name = "sop")
    public float mSavingPercentageInShootout;

    public Player(int mJerseyNumber, int mTeamId, String mPlayerName, String mPosition) {
        this.mJerseyNumber = mJerseyNumber;
        this.mTeamId = mTeamId;
        this.mPlayerName = mPlayerName;
        this.mPosition = mPosition;
    }
}
