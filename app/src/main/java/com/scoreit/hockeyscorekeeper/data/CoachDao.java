package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Coach;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CoachDao {
    @Insert
    void insert(Coach coach);

    @Update
    void update(Coach coach);

    @Query("SELECT * FROM coach_table")
    LiveData<List<Coach>> getAll();

    @Delete
    void delete(Coach coach);

    @Query("SELECT * FROM coach_table WHERE teamId = :teamId")
    LiveData<List<Coach>> getCoachesByTeam(int teamId);

    @Query("SELECT * FROM coach_table WHERE teamId = :teamId AND id = :coachId")
    LiveData<Coach> getCoach(int teamId, int coachId);
}
