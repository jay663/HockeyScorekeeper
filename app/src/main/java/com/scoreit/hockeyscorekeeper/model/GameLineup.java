package com.scoreit.hockeyscorekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "game_lineup_table",
        primaryKeys = {"gameId", "teamId", "jerseyNumber", "awayOrHome" })
public class GameLineup {

    @NonNull
    @ForeignKey(entity = Game.class, parentColumns = "id", childColumns = "gameId", onDelete = CASCADE)
    @ColumnInfo(name = "gameId")
    public long mGameId;

    @NonNull
    @ForeignKey(entity = Team.class, parentColumns = "teamId", childColumns = "teamId")
    @ColumnInfo(name = "teamId")
    public int mTeamId;

    @NonNull
    @ForeignKey(entity = Player.class, parentColumns = "jerseyNumber", childColumns = "jerseyNumber")
    @ColumnInfo(name = "jerseyNumber")
    public int mJerseyNumber;

    @ColumnInfo(name = "playerName")
    public String mPlayerName;

    @NonNull
    @ColumnInfo(name = "awayOrHome")
    public String mAwayorHome;

    @NonNull
    @ColumnInfo(name = "position")
    public String mPosition;

    @ColumnInfo(name = "line")
    public String mLine;

    @ColumnInfo(name = "status")
    public String mStatus;

    @ColumnInfo(name = "toi")
    public Long mTimeOnIce;

    @ColumnInfo(name = "even_strength_toi")
    public Long mEvenStrengthTimeOnIce;

    @ColumnInfo(name = "power_play_toi")
    public Long mPowerPlayTOI;

    public GameLineup(long mGameId, int mJerseyNumber, int mTeamId, String mPosition, String mLine, String mAwayorHome, String mStatus, String playerName) {
        this.mGameId = mGameId;
        this.mTeamId = mTeamId;
        this.mJerseyNumber = mJerseyNumber;
        this.mAwayorHome = mAwayorHome;
        this.mPosition = mPosition;
        this.mLine = mLine;
        this.mStatus = mStatus;
        mTimeOnIce = 0L;
        mEvenStrengthTimeOnIce = 0L;
        mPowerPlayTOI = 0L;
        mPlayerName = playerName;
    }
}

/**
 *  What will I need in the Lineup table
 *  I suppose the lineup could be created and not be associated with a game
 *  Typically you would want to associate a lineup with a game
 *  I also need within a game to be able to do the following
 *   - record the scoring of a player
 *   - record the time that player was on the ice
 *
 *   @Entity(tableName = "game_lineup_table",
 * primaryKeys = {"gameId", "teamId", "jerseyNumber", "awayOrHome" })
 */
