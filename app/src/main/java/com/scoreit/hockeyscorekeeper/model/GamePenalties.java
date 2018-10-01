package com.scoreit.hockeyscorekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "game_penalties_table",
        indices = {@Index(value = {"gameId", "homeOrAway"})})
public class GamePenalties {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @NonNull
    @ColumnInfo(name = "jerseyNumber")
    public int jerseyNumber;

    @NonNull
    @ForeignKey(entity = Game.class, parentColumns = "id", childColumns = "gameId", onDelete = CASCADE)
    @ColumnInfo(name = "gameId")
    public long gameId;

    @ColumnInfo(name = "homeOrAway")
    public String homeOrAway;

    @ColumnInfo(name = "period")
    public int period;

    @ColumnInfo(name = "teamId")
    public int teamId;

    @ColumnInfo(name = "penalty")
    public String penalty;

    @ColumnInfo(name = "min")
    public String minutes;

    @ColumnInfo(name = "startTime")
    public String startTime;


    public GamePenalties(int jerseyNumber, long gameId, String homeOrAway, int period, int teamId, String penalty, String minutes, String startTime) {
        this.jerseyNumber = jerseyNumber;
        this.gameId = gameId;
        this.homeOrAway = homeOrAway;
        this.period = period;
        this.teamId = teamId;
        this.penalty = penalty;
        this.minutes = minutes;
        this.startTime = startTime;
    }
}
