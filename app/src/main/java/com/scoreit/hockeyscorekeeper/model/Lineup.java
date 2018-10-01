package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "lineup_table")
public class Lineup {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "jerseyNumber")
    public int mJerseyNumber;

    @ColumnInfo(name = "teamId")
    public int mTeamId;

    @ColumnInfo(name = "awayOrHome")
    public String mAwayorHome;

    @ColumnInfo(name = "position")
    public String mPosition;

    @ColumnInfo(name = "line")
    public String mline;

    public Lineup(int mJerseyNumber, int mTeamId, String mPosition, String mline, String mAwayorHome) {
        this.mJerseyNumber = mJerseyNumber;
        this.mTeamId = mTeamId;
        this.mPosition = mPosition;
        this.mline = mline;
        this.mAwayorHome = mAwayorHome;
    }
}

/**
 *  What will I need in the Lineup table
 *  I suppose the lineup could be created and not be associated with a game
 *  Typically you would want to associate a lineup with a game
 *  I also need within a game to be able to do the following
 *   - record the scoring of a player
 *   - record the time that player was on the ice
 */
