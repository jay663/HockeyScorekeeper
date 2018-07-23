package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "coach_table")
public class Coach {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "name")
    @NonNull
    public String mName;

    @ColumnInfo(name = "teamId")
    @NonNull
    public int mTeamId;

    @ColumnInfo(name = "typeOfCoach")
    public String mTypeOfCoach;

    public Coach(@NonNull int id, @NonNull String mName, @NonNull int mTeamId, String mTypeOfCoach) {
        this.id = id;
        this.mName = mName;
        this.mTeamId = mTeamId;
        this.mTypeOfCoach = mTypeOfCoach;
    }
}
