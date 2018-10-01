package com.scoreit.hockeyscorekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "team_table",
        indices = {@Index(value = {"teamName", "location"}, unique = true)})
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



