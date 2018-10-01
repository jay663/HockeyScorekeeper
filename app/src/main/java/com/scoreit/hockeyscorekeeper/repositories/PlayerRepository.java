package com.scoreit.hockeyscorekeeper.repositories;

import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface PlayerRepository {
    LiveData<List<Player>> getAllPlayers();

    LiveData<Player> getPlayer (int teamId, int playerId);

    LiveData<List<Player>> getTeamPlayers(int teamId);

    void addPlayer (Player player);

    void removePlayer(Player player);

    void updatePlayer(Player player);
}
