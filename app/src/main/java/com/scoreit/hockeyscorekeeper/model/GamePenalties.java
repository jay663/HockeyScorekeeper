package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "game_penalties_table")
public class GamePenalties {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "jerseyNumber")
    public int mJerseyNumber;

    @ColumnInfo(name = "period")
    public int mPeriod;

    @ColumnInfo(name = "teamId")
    public int mTeamId;

    @ColumnInfo(name = "homeOrAway")
    public String mHomeOrAway;

    @ColumnInfo(name = "penalty")
    public String mPenalty;

    @ColumnInfo(name = "min")
    public String mMinutes;

    @ColumnInfo(name = "startTime")
    public String mStartTime;

    public GamePenalties(int mPeriod, int mJerseyNumber, int mTeamId, String mHomeOrAway,
                         String mPenalty, String mMinutes, String mStartTime) {
        this.mPeriod = mPeriod;
        this.mJerseyNumber = mJerseyNumber;
        this.mTeamId = mTeamId;
        this.mHomeOrAway = mHomeOrAway;
        this.mPenalty = mPenalty;
        this.mMinutes = mMinutes;
        this.mStartTime = mStartTime;
    }
}
