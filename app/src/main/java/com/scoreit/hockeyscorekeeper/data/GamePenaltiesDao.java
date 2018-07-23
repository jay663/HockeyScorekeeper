package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.GamePenalties;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GamePenaltiesDao {
    @Insert
    void insertAll(GamePenalties gamePenalties);

    @Update
    void updateAll(GamePenalties gamePenalties);

    @Query("SELECT * FROM game_penalties_table")
    List<GamePenalties> getAll();

    @Delete
    void deleteAll(GamePenalties gamePenalties);
}
