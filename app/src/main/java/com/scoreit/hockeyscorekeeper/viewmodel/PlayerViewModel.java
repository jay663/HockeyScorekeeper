package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;

import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.PlayerDao;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.repositories.PlayerRepository;
import com.scoreit.hockeyscorekeeper.repositories.PlayerRepositoryImpl;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository mRepository;
    private LiveData<List<Player>> mPlayers;

    /**
    Player players[] =
            {
                    new Player(8, 8, "Joe Granger", "C"),
                    new Player(9, 8, "Zachery Orange", "LW"),
                    new Player(27, 8, "Joshua Noon", "RW"),
                    new Player(47, 8, "Matt Blue", "D"),
                    new Player(88, 8, "Isaac Carr", "D"),
                    new Player(31, 8, "Sergei Green", "G"),
                    new Player(48, 8, "Aaron Cole", "C"),
                    new Player(39, 8, "Harry Hall", "LW"),
                    new Player(89, 8, "Terrance Bicolli", "RW"),
                    new Player(44, 8, "Marc Villangio", "D"),
                    new Player(61, 8, "Dave Red", "D"),
                    new Player(30, 8, "Dominic Mitchell", "G"),

                    new Player(18, 9, "Pierre Lucra", "C"),
                    new Player(9, 9, "Murray Ferris", "LW"),
                    new Player(13, 9, "Jasper Ghost", "RW"),
                    new Player(8, 9, "Manny Carbone", "D"),
                    new Player(3, 9, "Harris Wininger", "D"),
                    new Player(72, 9, "	Randy Grayson", "G"),
                    new Player(10, 9, "Alexander Savardie", "C"),
                    new Player(38, 9, "Tom Partie", "LW"),
                    new Player(26, 9, "Oscar Doever", "RW"),
                    new Player(23, 9, "	Mario Caparzo", "D"),
                    new Player(58, 9, "	Sam Hill", "D"),
                    new Player(70, 9, "Dennis Calverta", "G"),

                    new Player(55, 10, "Gary Slater", "C"),
                    new Player(81, 10, "Kyle Smith", "LW"),
                    new Player(26, 10, "Mick Blake", "RW"),
                    new Player(44, 10, "Jerry Morrisson", "D"),
                    new Player(8, 10, "Larry Trubell", "D"),
                    new Player(37, 10, "	Branson Elton", "G"),
                    new Player(25, 10, "Derrick Easton", "C"),
                    new Player(13, 10, "Perry Mummy", "LW"),
                    new Player(29, 10, "Cris Lane", "RW"),
                    new Player(39, 10, "	Tanner Miller", "D"),
                    new Player(33, 10, "	Dan Bruller", "D"),
                    new Player(35, 10, "Madden George", "G")

            };
    **/

    public PlayerViewModel(Application application) {
        super(application);
        PlayerDao dao = HockeyDatabase.getDatabase(application).getPlayerDao();
        mRepository = new PlayerRepositoryImpl(dao);
        mPlayers = mRepository.getAllPlayers();
    }

    public LiveData<List<Player>> getTeamPlayers(int teamId) { return mRepository.getTeamPlayers(teamId); }

    public void insert(Player player) { mRepository.addPlayer(player); }

    public void deletePlayer(Player player) {
        mRepository.removePlayer(player);
    }

    public LiveData<Player> getPlayer(int teamId, int playerId) {
        return mRepository.getPlayer(teamId, playerId);
    }

    public void update(Player player) {
        mRepository.updatePlayer(player);
    }
}
