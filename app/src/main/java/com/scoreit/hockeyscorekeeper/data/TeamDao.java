package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TeamDao {
    @Insert
    long insert(Team team);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertOnStartup(Team team);

    @Update
    void update(Team team);

    @Query("SELECT * FROM team_table")
    LiveData<List<Team>> getAll();

    @Delete
    void delete(Team team);

    @Query("Delete FROM team_table WHERE teamName = :teamName AND location = :teamLocation")
    void delete(String teamName, String teamLocation);

    @Query("SELECT * FROM team_table WHERE teamId = :teamId")
    LiveData<Team> getTeam(int teamId);

    @Query("SELECT * FROM team_table WHERE teamName = :teamName AND location = :teamLocation")
    LiveData<Team> getTeam(String teamName, String teamLocation);

    @Query("SELECT * FROM team_table WHERE teamId IN (:teams)")
    LiveData<List<Team>> getGameEligibleTeams(int[] teams);

}
