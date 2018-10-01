package com.scoreit.hockeyscorekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "game_shots_table",
        primaryKeys = {"gameId"})
public class GameShots {
    @NonNull
    @ForeignKey(entity = Game.class, parentColumns = "id", childColumns = "gameId", onDelete = CASCADE)
    @ColumnInfo(name = "gameId")
    public long gameId;
    @ColumnInfo(name = "homePeriod1Shots")
    public int homePeriod1Shots;
    @ColumnInfo(name = "homePeriod2Shots")
    public int homePeriod2Shots;
    @ColumnInfo(name = "homePeriod3Shots")
    public int homePeriod3Shots;
    @ColumnInfo(name = "homeOTShots")
    public int homeOTShots;
    @ColumnInfo(name = "homeShotTotal")
    public int homeShotTotal;
    @ColumnInfo(name = "awayPeriod1Shots")
    public int awayPeriod1Shots;
    @ColumnInfo(name = "awayPeriod2Shots")
    public int awayPeriod2Shots;
    @ColumnInfo(name = "awayPeriod3Shots")
    public int awayPeriod3Shots;
    @ColumnInfo(name = "awayOTShots")
    public int awayOTShots;
    @ColumnInfo(name = "awayShotTotal")
    public int awayShotTotal;

    public GameShots(long gameId) {
        this.gameId = gameId;

    }
}
