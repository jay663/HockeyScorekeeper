package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Coach;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
interface CoachDao {
    @Insert
    void insertAll(Coach coach);

    @Update
    void updateAll(Coach coach);

    @Query("SELECT * FROM coach_table")
    List<Coach> getAll();

    @Delete
    void deleteAll(Coach coach);
}
