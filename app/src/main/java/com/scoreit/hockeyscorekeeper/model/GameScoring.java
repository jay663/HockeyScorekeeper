package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_scoring_table")
public class GameScoring {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "gameId")
    public long gameId;

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

    public GameScoring(long gameId, int teamId, String homeOrAway, int period, int scoringPlayerJersey, String gameTime, int firstAssistPlayer, int secondaryAssistPlayer) {
        this.gameId = gameId;
        this.teamId = teamId;
        this.homeOrAway = homeOrAway;
        this.sequence = 0;
        this.period = period;
        this.scoringPlayerJersey = scoringPlayerJersey;
        this.gameTime = gameTime;
        this.firstAssistPlayer = firstAssistPlayer;
        this.secondaryAssistPlayer = secondaryAssistPlayer;
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
