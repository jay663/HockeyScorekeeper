package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.Lineup;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LineupDao {
    @Insert
    void insertAll(Lineup lineup);

    @Update
    void updateAll(Lineup lineup);

    @Query("SELECT * FROM lineup_table")
    List<Lineup> getAll();

    @Delete
    void deleteAll(Lineup lineup);
}
