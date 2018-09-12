package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.GameLineup;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GameLineupDao {
    @Insert
    void insertAll(List<GameLineup> lineup);

    @Update
    void update(GameLineup lineup);

    @Query("SELECT * FROM game_lineup_table")
    LiveData<List<GameLineup>> getAll();

    @Delete
    void delete(GameLineup lineup);
}
