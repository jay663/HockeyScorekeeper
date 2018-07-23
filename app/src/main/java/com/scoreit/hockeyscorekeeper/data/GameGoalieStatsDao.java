package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.GameGoalieStats;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
interface GameGoalieStatsDao {
    @Insert
    void insertAll(GameGoalieStats gameGoalieStats);

    @Update
    void updateAll(GameGoalieStats gameGoalieStats);

    @Query("SELECT * FROM game_goalie_stats_table")
    List<GameGoalieStats> getAll();

    @Delete
    void deleteAll(GameGoalieStats gameGoalieStats);
}
