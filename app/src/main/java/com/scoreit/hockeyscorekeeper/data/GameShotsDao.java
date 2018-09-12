package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.GameShots;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GameShotsDao {
    @Insert
    void insert(GameShots gameShots);

    @Update
    void update(GameShots gameShots);

    @Delete
    void delete(GameShots gameShots);

    @Query("SELECT * FROM game_shots_table WHERE gameId = :gameId")
    LiveData<GameShots> getGameShots(long gameId);
}
