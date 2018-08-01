package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "coach_table")
public class Coach {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @NonNull
    @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "teamId", onDelete = CASCADE)
    @ColumnInfo(name = "teamId")
    public int mTeamId;

    @ColumnInfo(name = "name")
    @NonNull
    public String mName;

    @ColumnInfo(name = "typeOfCoach")
    public String mTypeOfCoach;

    public Coach(@NonNull String mName, @NonNull int mTeamId, String mTypeOfCoach) {
        this.mName = mName;
        this.mTeamId = mTeamId;
        this.mTypeOfCoach = mTypeOfCoach;
    }
}
