package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public interface GameLineupRepository {
    void addGameLineup(ArrayList lineup);

    LiveData<List<Player>> getActivePlayers(long gameId, int teamId, String homeOrAway);
}
