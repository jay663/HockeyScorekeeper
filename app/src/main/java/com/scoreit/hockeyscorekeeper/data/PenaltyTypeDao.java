package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.PenaltyType;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface PenaltyTypeDao {
    @Insert
    void insertAll(PenaltyType penaltyType);

    @Update
    void updateAll(PenaltyType penaltyType);

    @Query("SELECT * FROM penalty_type_table")
    List<PenaltyType> getAll();

    @Delete
    void deleteAll(PenaltyType penaltyType);
}
