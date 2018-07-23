package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_scoring_table")
public class GameScoring {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "teamId")
    public int teamId;

    @ColumnInfo(name = "homeOrAway")
    public String homeOrAway;

    @ColumnInfo(name = "goalSequence")
    public int sequence;

    @ColumnInfo(name = "period")
    public int period;

    @ColumnInfo(name = "scoringPlayer")
    public int scoringPlayerJersey;

    @ColumnInfo(name = "gameTime")
    public String gameTime;

    @ColumnInfo(name = "firstAssistPlayer")
    public int firstAssistPlayer;

    @ColumnInfo(name = "secondaryAssistPlayer")
    public int secondaryAssistPlayer;

    public GameScoring() {
    }

    //    public GameScoring(int TeamId, String HomeOrAway, int Sequence,
//                       int Period, int ScoringPlayerJersey, String GameTime,
//                       int FirstAssistPlayer, int SecondaryAssistPlayer) {
//        this.teamId = TeamId;
//        this.homeOrAway = HomeOrAway;
//        this.sequence = Sequence;
//        this.period = Period;
//        this.scoringPlayerJersey = ScoringPlayerJersey;
//        this.gameTime = GameTime;
//        this.firstAssistPlayer = FirstAssistPlayer;
//        this.secondaryAssistPlayer = SecondaryAssistPlayer;
//    }
}
