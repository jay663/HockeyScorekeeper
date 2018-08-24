package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.scoreit.hockeyscorekeeper.HockeyRepository;
import com.scoreit.hockeyscorekeeper.PlayerArrayAdapter;
import com.scoreit.hockeyscorekeeper.R;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;

public class SetLineupViewModel extends AndroidViewModel {
    Team[] teams = {
            new Team("Lakers", "Cleveland", "Lakers"),
            new Team("Seagulls", "Baltimore", "Seagulls"),
            new Team("Nashville Grapes", "Grapes", "Nashville")};

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
                    new Player(37, 10, "Branson Elton", "G"),
                    new Player(25, 10, "Derrick Easton", "C"),
                    new Player(13, 10, "Perry Mummy", "LW"),
                    new Player(29, 10, "Cris Lane", "RW"),
                    new Player(39, 10, "Tanner Miller", "D"),
                    new Player(33, 10, "Dan Bruller", "D"),
                    new Player(35, 10, "Madden George", "G")
            };


    private HockeyRepository mRepository;
    private List<Player> mPlayers;
    private int mTeamId;
    private boolean mIsSettingHomeLineup;
    private int mHomeTeamId;
    private int mAwayTeamId;
    private String mTitle;

    // figure out how to use the Observable I can't extend baseobservable
    //Observable<Player>
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

    public void setAwayTeamId(int mAwayTeamId) {
        this.mAwayTeamId = mAwayTeamId;
    }

    public String getTitle() {
        return mTitle;
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

    public SetLineupViewModel(Application application) {
        super(application);
        mPlayers = Arrays.asList(players);
        mRepository = new HockeyRepository(application);
    }

    public void initialize(int homeTeamId, int awayTeamId) {
        setHomeTeamId(homeTeamId);
        setAwayTeamId(awayTeamId);

        if(mHomeTeamId > -1){
            setTeamId(homeTeamId);
            setIsSettingHomeLineup(true);
        }else{
            setTeamId(awayTeamId);
            setIsSettingHomeLineup(false);
        }

        mTitle = String.format("Set %s Lineup", teams[mTeamId]);
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

    public List<Player> getPlayers()
    {
        return mPlayers;
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

    }
}
