package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Game;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GameDao {
    @Insert
    long insert(Game game);

    @Update
    void update(Game game);

    @Query("SELECT * FROM game_table")
    LiveData<List<Game>> getAll();

    @Query("SELECT * FROM game_table WHERE id = :gameId")
    LiveData<Game> getGame(long gameId);

    @Delete
    void delete(Game game);
}
