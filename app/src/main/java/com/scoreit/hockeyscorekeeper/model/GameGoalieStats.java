package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "game_goalie_stats_table")
public class GameGoalieStats {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "jerseyNumber")
    public int mJerseyNumber;

    @ColumnInfo(name = "teamId")
    public int mTeamId;

    @ColumnInfo(name = "homeOrAway")
    public String mHomeOrAway;

    @ColumnInfo(name = "sa")
    public int mShotsAgainst;

    @ColumnInfo(name = "sv")
    public int mSaves;

    public GameGoalieStats(int mJerseyNumber, int mTeamId, String mHomeOrAway, int mShotsAgainst, int mSaves) {
        this.mJerseyNumber = mJerseyNumber;
        this.mTeamId = mTeamId;
        this.mHomeOrAway = mHomeOrAway;
        this.mShotsAgainst = mShotsAgainst;
        this.mSaves = mSaves;
    }
}
