package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TeamDao {
    @Insert
    long insert(Team team);

    @Update
    void update(Team team);

    @Query("SELECT * FROM team_table")
    LiveData<List<Team>> getAll();

    @Delete
    void delete(Team team);

}
