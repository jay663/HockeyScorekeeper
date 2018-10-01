package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;

public class Scoreboard {
    @ColumnInfo(name = "homePeriod1Goals")
    public int homePeriod1Goals;

    @ColumnInfo(name = "homePeriod2Goals")
    public int homePeriod2Goals;

    @ColumnInfo(name = "homePeriod3Goals")
    public int homePeriod3Goals;

    @ColumnInfo(name = "homeOTGoals")
    public int homeOTGoals;

    @ColumnInfo(name = "homeFinalScore")
    public int homeFinalScore;

    @ColumnInfo(name = "awayPeriod1Goals")
    public int awayPeriod1Goals;

    @ColumnInfo(name = "awayPeriod2Goals")
    public int awayPeriod2Goals;

    @ColumnInfo(name = "awayPeriod3Goals")
    public int awayPeriod3Goals;

    @ColumnInfo(name = "awayOTGoals")
    public int awayOTGoals;

    @ColumnInfo(name = "awayfinalScore")
    public int awayFinalScore;

    public Scoreboard() {
        this.homePeriod1Goals = 0;
        this.homePeriod2Goals = 0;
        this.homePeriod3Goals = 0;
        this.homeOTGoals = 0;
        this.homeFinalScore = 0;
        this.awayPeriod1Goals = 0;
        this.awayPeriod2Goals = 0;
        this.awayPeriod3Goals = 0;
        this.awayOTGoals = 0;
        this.awayFinalScore = 0;
    }
}
