package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.scoreit.hockeyscorekeeper.R;
import com.scoreit.hockeyscorekeeper.adapters.PlayerArrayAdapter;
import com.scoreit.hockeyscorekeeper.data.GameLineupDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.data.PlayerDao;
import com.scoreit.hockeyscorekeeper.data.TeamDao;
import com.scoreit.hockeyscorekeeper.model.GameLineup;
import com.scoreit.hockeyscorekeeper.model.LineupStatus;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.repositories.GameLineupRepository;
import com.scoreit.hockeyscorekeeper.repositories.GameLineupRepositoryImpl;
import com.scoreit.hockeyscorekeeper.repositories.PlayerRepository;
import com.scoreit.hockeyscorekeeper.repositories.PlayerRepositoryImpl;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepository;
import com.scoreit.hockeyscorekeeper.repositories.TeamRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SetLineupViewModel extends AndroidViewModel {
    private GameLineupRepository mGameLineupRepository;
    private TeamRepository mTeamRepository;
    private PlayerRepository mPlayerRepository;

    private LiveData<List<Player>> mPlayers;
    private int mTeamId;
    private boolean mIsSettingHomeLineup;
    private int mHomeTeamId;
    private int mAwayTeamId;
    private long mGameId;

    private Player mCenter;
    private Player mLW;
    private Player mRW;
    private Player mLD;
    private Player mRD;
    private Player mGoalie;

    private ArrayAdapter<Player> mLWArrayAdapter;
    private ArrayAdapter<Player> mCArrayAdapter;
    private PlayerArrayAdapter mRWArrayAdapter;
    private PlayerArrayAdapter mLDArrayAdapter;
    private PlayerArrayAdapter mRDArrayAdapter;
    private PlayerArrayAdapter mGArrayAdapter;


    public SetLineupViewModel(Application application) {
        super(application);
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        TeamDao teamDao = db.getTeamDao();
        PlayerDao playerDao = db.getPlayerDao();
        GameLineupDao lineupDao = db.getGameLineupDao();

        mTeamRepository = new TeamRepositoryImpl(teamDao);
        mPlayerRepository = new PlayerRepositoryImpl(playerDao);
        mGameLineupRepository = new GameLineupRepositoryImpl(lineupDao);
    }

    public void initialize(int homeTeamId, int awayTeamId, long gameId) {
        setHomeTeamId(homeTeamId);
        setAwayTeamId(awayTeamId);
        setGameId(gameId);

        if(mHomeTeamId > -1){
            setTeamId(homeTeamId);
            setIsSettingHomeLineup(true);
        }else{
            setTeamId(awayTeamId);
            setIsSettingHomeLineup(false);
        }
    }

    public boolean isSettingHomeLineup() {
        return mIsSettingHomeLineup;
    }

    public void setIsSettingHomeLineup(boolean mIsSettingHomeLineup) {
        this.mIsSettingHomeLineup = mIsSettingHomeLineup;
    }

    public int getHomeTeamId() {
        return mHomeTeamId;
    }

    public void setHomeTeamId(int mHomeTeamId) {
        this.mHomeTeamId = mHomeTeamId;
    }

    public int getAwayTeamId() {
        return mAwayTeamId;
    }

    public long getGameId() {
        return mGameId;
    }

    public void setGameId(long mGameId) {
        this.mGameId = mGameId;
    }

    public void setAwayTeamId(int mAwayTeamId) {
        this.mAwayTeamId = mAwayTeamId;
    }

    public ArrayAdapter<Player> getLWArrayAdapter() {
        return mLWArrayAdapter;
    }

    public void setLWArrayAdapter(ArrayAdapter<Player> mLWArrayAdapter) {
        this.mLWArrayAdapter = mLWArrayAdapter;
    }

    public ArrayAdapter<Player> getCArrayAdapter() {
        return mCArrayAdapter;
    }

    public void setCArrayAdapter(ArrayAdapter<Player> mCArrayAdapter) {
        this.mCArrayAdapter = mCArrayAdapter;
    }

    public PlayerArrayAdapter getRWArrayAdapter() {
        return mRWArrayAdapter;
    }

    public void setRWArrayAdapter(PlayerArrayAdapter mRWArrayAdapter) {
        this.mRWArrayAdapter = mRWArrayAdapter;
    }

    public PlayerArrayAdapter getLDArrayAdapter() {
        return mLDArrayAdapter;
    }

    public void setLDArrayAdapter(PlayerArrayAdapter mLDArrayAdapter) {
        this.mLDArrayAdapter = mLDArrayAdapter;
    }

    public PlayerArrayAdapter getRDArrayAdapter() {
        return mRDArrayAdapter;
    }

    public void setRDArrayAdapter(PlayerArrayAdapter mRDArrayAdapter) {
        this.mRDArrayAdapter = mRDArrayAdapter;
    }

    public PlayerArrayAdapter getGArrayAdapter() {
        return mGArrayAdapter;
    }

    public void setGArrayAdapter(PlayerArrayAdapter mGArrayAdapter) {
        this.mGArrayAdapter = mGArrayAdapter;
    }

    public int getTeamId() {
        return mTeamId;
    }

    public void setTeamId(int mTeamId) {
        this.mTeamId = mTeamId;
    }

    public Player getCenter() {
        return mCenter;
    }

    public void setCenter(Player mCenter) {
        this.mCenter = mCenter;
    }

    public Player getLW() {
        return mLW;
    }

    public void setLW(Player mLW) {
        this.mLW = mLW;
    }

    public Player getRW() {
        return mRW;
    }

    public void setRW(Player mRW) {
        this.mRW = mRW;
    }

    public Player getLD() {
        return mLD;
    }

    public void setLD(Player mLD) {
        this.mLD = mLD;
    }

    public Player getRD() {
        return mRD;
    }

    public void setRD(Player mRD) {
        this.mRD = mRD;
    }

    public Player getGoalie() {
        return mGoalie;
    }

    public void setGoalie(Player mGoalie) {
        this.mGoalie = mGoalie;
    }

    public LiveData<List<Player>> getTeamPlayers(){
        return mPlayerRepository.getTeamPlayers(mTeamId);
    }

    public LiveData<Team> getTeam(){
        return mTeamRepository.getTeam(mTeamId);
    }

    public void filter(Player player, ArrayAdapter<Player> arrayAdapter, Player selectedPlayer) {
        StringBuilder builder = new StringBuilder();
        if (mGoalie != null){
            builder.append(mGoalie.mJerseyNumber);
            builder.append(" ");
        }

        if (mLW != null){
            builder.append(mLW.mJerseyNumber);
            builder.append(" ");
        }

        if (mCenter != null){
            builder.append(mCenter.mJerseyNumber);
            builder.append(" ");
        }

        if (mRW != null){
            builder.append(mRW.mJerseyNumber);
            builder.append(" ");
        }

        if (mLD != null){
            builder.append(mLD.mJerseyNumber);
            builder.append(" ");
        }

        if (mRD != null){
            builder.append(mRD.mJerseyNumber);
        }

        arrayAdapter.getFilter().filter(builder.toString());
    }

    public void filter(ArrayAdapter<Player> arrayAdapter) {
        StringBuilder builder = new StringBuilder();
        if (mGoalie != null){
            builder.append(mGoalie.mJerseyNumber);
            builder.append(" ");
        }

        if (mLW != null){
            builder.append(mLW.mJerseyNumber);
            builder.append(" ");
        }

        if (mCenter != null){
            builder.append(mCenter.mJerseyNumber);
            builder.append(" ");
        }

        if (mRW != null){
            builder.append(mRW.mJerseyNumber);
            builder.append(" ");
        }

        if (mLD != null){
            builder.append(mLD.mJerseyNumber);
            builder.append(" ");
        }

        if (mRD != null){
            builder.append(mRD.mJerseyNumber);
        }

        arrayAdapter.getFilter().filter(builder.toString());
    }

    public void onPlayerSelected(AdapterView<?> adapterView, Player player) {

        // 2) Determine spinner triggered
        switch (adapterView.getId()){
            case R.id.set_lineup_lw_spinner:
                setLW(player);
                filter(mCArrayAdapter);
                filter(mRWArrayAdapter);
                filter(mLDArrayAdapter);
                filter(mRDArrayAdapter);
                filter(mGArrayAdapter);
                break;
            case R.id.set_lineup_c_spinner:
                setCenter(player);
                filter(mLWArrayAdapter);
                filter(mRWArrayAdapter);
                filter(mLDArrayAdapter);
                filter(mRDArrayAdapter);
                filter(mGArrayAdapter);
                break;
            case R.id.set_lineup_rw_spinner:
                setRW(player);
                filter(mCArrayAdapter);
                filter(mLWArrayAdapter);
                filter(mLDArrayAdapter);
                filter(mRDArrayAdapter);
                filter(mGArrayAdapter);
                break;
            case R.id.set_lineup_leftd_spinner:
                setLD(player);
                filter(mCArrayAdapter);
                filter(mLWArrayAdapter);
                filter(mRWArrayAdapter);
                filter(mRDArrayAdapter);
                filter(mGArrayAdapter);
                break;
            case R.id.set_lineup_rightd_spinner:
                setRD(player);
                filter(mCArrayAdapter);
                filter(mLWArrayAdapter);
                filter(mRWArrayAdapter);
                filter(mLDArrayAdapter);
                filter(mGArrayAdapter);
                break;
            case R.id.set_lineup_goalie_spinner:
                setGoalie(player);
                filter(mCArrayAdapter);
                filter(mLWArrayAdapter);
                filter(mRWArrayAdapter);
                filter(mLDArrayAdapter);
                filter(mRDArrayAdapter);
                break;

        }

        // 3) Update View Models Player in Lineup

    }

    public void saveLineup(){
        String homeOrAway = "";
        if (mIsSettingHomeLineup){
            homeOrAway = "home";
        }else{
            homeOrAway = "away";
        }

        ArrayList lineup = new ArrayList();
        GameLineup lineupEntry = new GameLineup(mGameId, mLW.mJerseyNumber, mTeamId, "LW", "1", homeOrAway, LineupStatus.ON_ICE, mLW.mPlayerName);
        lineup.add(lineupEntry);
        lineupEntry = new GameLineup(mGameId, mCenter.mJerseyNumber, mTeamId, "C", "1", homeOrAway, LineupStatus.ON_ICE, mCenter.mPlayerName);
        lineup.add(lineupEntry);
        lineupEntry = new GameLineup(mGameId, mRW.mJerseyNumber, mTeamId, "RW", "1", homeOrAway, LineupStatus.ON_ICE, mRW.mPlayerName);
        lineup.add(lineupEntry);
        lineupEntry = new GameLineup(mGameId, mLD.mJerseyNumber, mTeamId, "LD", "1", homeOrAway, LineupStatus.ON_ICE, mLD.mPlayerName );
        lineup.add(lineupEntry);
        lineupEntry = new GameLineup(mGameId, mRD.mJerseyNumber, mTeamId, "RD", "1", homeOrAway, LineupStatus.ON_ICE, mRD.mPlayerName);
        lineup.add(lineupEntry);
        lineupEntry = new GameLineup(mGameId, mGoalie.mJerseyNumber, mTeamId, "G", "1", homeOrAway, LineupStatus.ON_ICE, mGoalie.mPlayerName);
        lineup.add(lineupEntry);

        mGameLineupRepository.addGameLineup(lineup);
    }


}
