package com.scoreit.hockeyscorekeeper.data;

import com.scoreit.hockeyscorekeeper.model.RosterCount;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface RosterCountDao {
    @Query("SELECT teamId, count(*) as player_count FROM player_table GROUP BY teamId HAVING count(*) > :minCount")
    public LiveData<List<RosterCount>> eligibleTeamIds(int minCount);
}
