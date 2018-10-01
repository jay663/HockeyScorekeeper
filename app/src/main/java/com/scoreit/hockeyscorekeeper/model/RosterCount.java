package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class RosterCount {
    @ColumnInfo(name = "teamId")
    public int teamId;

    @ColumnInfo(name="player_count")
    public long count;

    public RosterCount() {
    }

    @Ignore
    public RosterCount(int teamId, long count) {
        this.teamId = teamId;
        this.count = count;
    }
}
