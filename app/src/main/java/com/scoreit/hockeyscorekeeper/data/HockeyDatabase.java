package com.scoreit.hockeyscorekeeper.data;

import android.content.Context;
import android.os.AsyncTask;

import com.scoreit.hockeyscorekeeper.model.Coach;
import com.scoreit.hockeyscorekeeper.model.Game;
import com.scoreit.hockeyscorekeeper.model.GameGoalieStats;
import com.scoreit.hockeyscorekeeper.model.GamePenalties;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.model.Lineup;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Coach.class, Game.class, GameGoalieStats.class, GamePenalties.class, GameScoring.class,
Lineup.class, PenaltyType.class, Player.class, Team.class}, version = 1 , exportSchema = false)
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
            String teamName = "Lakers";
            String location = "Cleveland";

            // TODO: Delete Records of Defaults
            //Team defaultTeam = new Team("Lakers", "Cleveland", "Lakers");


            // TODO: Add Records of Defaults
            //mDao.deleteAll();
            //mDao.insert(word);
            return null;
        }
    }
}
