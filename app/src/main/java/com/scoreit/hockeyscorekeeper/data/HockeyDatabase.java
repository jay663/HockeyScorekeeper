package com.scoreit.hockeyscorekeeper.data;

import android.content.Context;
import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.model.Coach;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameGoalieStats;
import com.scoreit.hockeyscorekeeper.model.GameLineup;
import com.scoreit.hockeyscorekeeper.model.GamePenalties;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.model.GameShots;
import com.scoreit.hockeyscorekeeper.model.Lineup;
import com.scoreit.hockeyscorekeeper.model.PenaltyType;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Coach.class, Game.class, GameGoalieStats.class, GamePenalties.class, GameScoring.class,
Lineup.class, PenaltyType.class, Player.class, Team.class, GameLineup.class, GameShots.class}, version = 1 , exportSchema = false)
public abstract class HockeyDatabase extends RoomDatabase {
    public static final String DB_NAME = "hockey_db";

    public abstract CoachDao getCoachDao();
    public abstract GameDao getGameDao();
    public abstract GameGoalieStatsDao getGameGoalieStatsDao();
    public abstract GamePenaltiesDao getPenaltiesDao();
    public abstract GameScoringDao getGameScoringDao();
    public abstract LineupDao getLineupDao();
    public abstract PenaltyTypeDao getPenaltyTypeDao();
    public abstract PlayerDao getPlayerDao();
    public abstract TeamDao getTeamDao();
    public abstract RosterCountDao getRosterCountDao();
    public abstract GameLineupDao getGameLineupDao();
    public abstract GameShotsDao getGameShotsDao();


    private static HockeyDatabase INSTANCE;

    public static HockeyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HockeyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HockeyDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TeamDao mTeamDao;
        private final PlayerDao mPlayerDao;

        PopulateDbAsync(HockeyDatabase db) {
            mTeamDao = db.getTeamDao();
            mPlayerDao = db.getPlayerDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Team defaultTeam = new Team("Lakers", "Cleveland", "Lakers");
            Team secondTeam = new Team("Lobsters", "Maine", "Lobsters");
            long result = -1L;
            int teamId = -1;

            Team team = mTeamDao.getTeam(defaultTeam.mTeamName, defaultTeam.mLocation).getValue();

            if (team == null){
                result = mTeamDao.insertOnStartup(defaultTeam);
                teamId = (int)result;
                if(result > 0){
                    mPlayerDao.insertOnStartup(new Player(8, teamId, "Joe Granger", "C"));
                    mPlayerDao.insertOnStartup(new Player(9, teamId, "Zachery Orange", "LW"));
                    mPlayerDao.insertOnStartup(new Player(27, teamId, "Joshua Noon", "RW"));
                    mPlayerDao.insertOnStartup(new Player(47, teamId, "Matt Blue", "D"));
                    mPlayerDao.insertOnStartup(new Player(88, teamId, "Isaac Carr", "D"));
                    mPlayerDao.insertOnStartup(new Player(31, teamId, "Sergei Green", "G"));
                    mPlayerDao.insertOnStartup(new Player(48, teamId, "Aaron Cole", "C"));
                    mPlayerDao.insertOnStartup(new Player(39, teamId, "Harry Hall", "LW"));
                    mPlayerDao.insertOnStartup(new Player(89, teamId, "Terrance Bicolli", "RW"));
                    mPlayerDao.insertOnStartup(new Player(44, teamId, "Marc Villangio", "D"));
                    mPlayerDao.insertOnStartup(new Player(61, teamId, "Dave Red", "D"));
                    mPlayerDao.insertOnStartup(new Player(30, teamId, "Dominic Mitchell", "G"));
                }
            }

            team = mTeamDao.getTeam(secondTeam.mTeamName, secondTeam.mLocation).getValue();

            if (team == null){
                result = mTeamDao.insertOnStartup(secondTeam);
                teamId = (int)result;
                if(result > 0) {
                    mPlayerDao.insertOnStartup(new Player(55, teamId, "Gary Slater", "C"));
                    mPlayerDao.insertOnStartup(new Player(81, teamId, "Kyle Smith", "LW"));
                    mPlayerDao.insertOnStartup(new Player(26, teamId, "Mick Blake", "RW"));
                    mPlayerDao.insertOnStartup(new Player(44, teamId, "Jerry Morrisson", "D"));
                    mPlayerDao.insertOnStartup(new Player(8, teamId, "Larry Trubell", "D"));
                    mPlayerDao.insertOnStartup(new Player(37, teamId, "Branson Elton", "G"));
                    mPlayerDao.insertOnStartup(new Player(25, teamId, "Derrick Easton", "C"));
                    mPlayerDao.insertOnStartup(new Player(13, teamId, "Perry Mummy", "LW"));
                    mPlayerDao.insertOnStartup(new Player(29, teamId, "Cris Lane", "RW"));
                    mPlayerDao.insertOnStartup(new Player(39, teamId, "Tanner Miller", "D"));
                    mPlayerDao.insertOnStartup(new Player(33, teamId, "Dan Bruller", "D"));
                    mPlayerDao.insertOnStartup(new Player(35, teamId, "Madden George", "G"));
                }
            }

            return null;
        }

    }
}
