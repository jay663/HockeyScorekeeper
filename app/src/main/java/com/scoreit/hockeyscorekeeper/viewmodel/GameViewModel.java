package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;
import android.content.Context;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.Arrays;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.LiveData;

public class GameViewModel extends BaseObservable {
    private HockeyRepository mRepository;
    private LiveData<List<Player>> mPlayers;

    public Game game;
    private Context context;

    Team[] teams = {new Team("San Jose Sharks", "Sharks", "San Jose"), new Team("Columbus Blue Jackets", "Blue Jackets", "Columbus"),
            new Team("Winnipeg Jets", "Jets", "Winnipeg"), new Team("Nashville Grapes", "Grapes", "Nashville")};


    Player players[] =
            {
                    new Player(8, 1, "Joe Pavelski", "C"),
                    new Player(9, 1, "Evander Kane", "LW"),
                    new Player(27, 1, "Joonas Donskoi", "RW"),
                    new Player(47, 1, "Joakim Ryan", "LD"),
                    new Player(88, 1, "Brent Burns", "RD"),
                    new Player(31, 1, "Martin Jones", "G"),
                    new Player(48, 1, "Logan Couture", "C"),
                    new Player(39, 1, "Tomas Hertl", "LW"),
                    new Player(89, 1, "Mikkel Boedker", "RW"),
                    new Player(44, 1, "Marc-Edouard Vlasic", "LD"),
                    new Player(61, 1, "Justin Braun", "RD"),
                    new Player(30, 1, "Aaron Dell", "G"),

                    new Player(18, 2, "Pierre-Luc Dubois", "C"),
                    new Player(9, 2, "Cam Atkinson", "LW"),
                    new Player(13, 2, "Artemi Panarin", "RW"),
                    new Player(8, 2, "Zach Werenski", "LD"),
                    new Player(3, 2, "Seth Jones", "RD"),
                    new Player(72, 2, "	Sergei Bobrovsky", "G"),
                    new Player(10, 2, "Alexander Wennberg", "C"),
                    new Player(38, 2, "Matt Calvert", "LW"),
                    new Player(26, 2, "Josh Anderson", "RW"),
                    new Player(23, 2, "	Ian Cole", "LD"),
                    new Player(58, 2, "	David Savard", "RD"),
                    new Player(70, 2, "Joonas Korpisalo", "G"),

                    new Player(55, 3, "Mark Scheifele", "C"),
                    new Player(81, 3, "Kyle Connor", "LW"),
                    new Player(26, 3, "Blake Wheeler", "RW"),
                    new Player(44, 3, "Josh Morrissey", "LD"),
                    new Player(8, 3, "Jacob Trouba", "RD"),
                    new Player(37, 3, "	Connor Hellebuyck", "G"),
                    new Player(25, 3, "Paul Stastny", "C"),
                    new Player(13, 3, "Brandon Tanev", "LW"),
                    new Player(29, 3, "Patrik Laine", "RW"),
                    new Player(39, 3, "	Toby Enstrom", "LD"),
                    new Player(33, 3, "	Dustin Byfuglien", "RD"),
                    new Player(35, 3, "Steve Mason", "G")
            };

    public GameViewModel(Application application, Context context, Game game) {
        mRepository = new HockeyRepository(application);
        this.context = context;
        this.game = game;
    }

    public List<Player> getPlayersByTeam(int teamId){
        List<Player> teamPlayers = Arrays.asList(players);
        teamPlayers.clear();
        for (Player player : players) {
            if (player.mTeamId == teamId)
            {
                teamPlayers.add(player);
            }
        }
        return teamPlayers;
    }

    
}
