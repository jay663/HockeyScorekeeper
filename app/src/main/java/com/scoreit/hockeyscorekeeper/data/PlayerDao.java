package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao            
public interface PlayerDao {
    @Insert
    void insert(Player player);

    @Update
    void update(Player player);

    @Query("SELECT * FROM player_table")
    LiveData<List<Player>> getAll();

    @Delete
    void delete(Player player);

    @Query("SELECT * FROM player_table WHERE teamId = :teamId")
    LiveData<List<Player>> getPlayersByTeam(int teamId);

    @Query("SELECT * FROM player_table WHERE teamId = :teamId AND jerseyNumber = :playerId")
    LiveData<Player> getPlayer(int teamId, int playerId);
}
