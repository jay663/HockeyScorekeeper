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
}
