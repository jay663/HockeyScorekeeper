package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "team_table")
public class Team {
    @ColumnInfo(name = "teamId")
    @PrimaryKey(autoGenerate = true)
    public int mTeamId;

    @ColumnInfo(name = "teamName")
    @NonNull
    public String mTeamName;

    @ColumnInfo(name = "location")
    public String mLocation;

    @ColumnInfo(name = "mascott")
    public String mMascott;

    public Team(@NonNull String mTeamName, String mLocation, String mMascott) {
        this.mTeamName = mTeamName;
        this.mLocation = mLocation;
        this.mMascott = mMascott;
    }

    @Override
    @Ignore
    public String toString()
    {
        return mTeamName;
    }
}



