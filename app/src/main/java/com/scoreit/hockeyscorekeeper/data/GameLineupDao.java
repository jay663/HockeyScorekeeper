package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.GameLineup;
import com.scoreit.hockeyscorekeeper.model.Player;

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


    @Query("SELECT * FROM player_table AS p "
            + "LEFT JOIN game_lineup_table AS lineup ON p.jerseyNumber = lineup.jerseyNumber "
            + "WHERE lineup.gameId = :gameId AND lineup.teamId = :teamId AND lineup.awayOrHome = :homeOrAway")
    LiveData<List<Player>> getActivePlayers(long gameId, int teamId, String homeOrAway);

}
