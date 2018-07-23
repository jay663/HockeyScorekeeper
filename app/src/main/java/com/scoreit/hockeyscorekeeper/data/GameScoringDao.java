package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.GameScoring;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GameScoringDao {
    @Insert
    void insertAll(GameScoring gameScoring);

    @Update
    void updateAll(GameScoring gameScoring);

    @Query("SELECT * FROM game_scoring_table")
    List<GameScoring> getAll();

    @Delete
    void deleteAll(GameScoring gameScoring);    
}
