package com.scoreit.hockeyscorekeeper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "penalty_type_table")
public class PenaltyType {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "penalty")
    public String mPenalty;

    @ColumnInfo(name = "category")
    public String mCategory;

    @ColumnInfo(name = "code")
    public String mPenaltyCode;

    @ColumnInfo(name = "penaltyBoxMinutes")
    public Long mPenaltyBoxMinutes;


    public PenaltyType(@NonNull String mPenalty, String mCategory) {
        this.mPenalty = mPenalty;
        this.mCategory = mCategory;
    }
}
