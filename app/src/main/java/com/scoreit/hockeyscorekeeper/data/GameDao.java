package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Game;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface GameDao {
    @Insert
    void insertAll(Game game);

    @Update
    void updateAll(Game game);

    @Query("SELECT * FROM game_table")
    List<Game> getAll();

    @Delete
    void deleteAll(Game game);    
}
