package com.scoreit.hockeyscorekeeper.viewmodel;

import android.app.Application;
import android.widget.AdapterView;

import com.scoreit.hockeyscorekeeper.GoalFragment;
import com.scoreit.hockeyscorekeeper.PlayGameActivity;
import com.scoreit.hockeyscorekeeper.R;
import com.scoreit.hockeyscorekeeper.adapters.PlayerArrayAdapter;
import com.scoreit.hockeyscorekeeper.data.GameDao;
import com.scoreit.hockeyscorekeeper.data.GameLineupDao;
import com.scoreit.hockeyscorekeeper.data.GameScoringDao;
import com.scoreit.hockeyscorekeeper.data.GameShotsDao;
import com.scoreit.hockeyscorekeeper.data.HockeyDatabase;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.repositories.GameLineupRepository;
import com.scoreit.hockeyscorekeeper.repositories.GameLineupRepositoryImpl;
import com.scoreit.hockeyscorekeeper.repositories.GameRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GoalViewModel extends AndroidViewModel {

    private GameRepository mRepository;
    private GameLineupRepository mGameLineupRepository;

    private long mGameId;
    private int mTeamId;
    private String mHomeOrAway;
    private int mCurrentPeriod;

    private Player mScorer;
    private Player mAssist;
    private Player mSecondaryAssist;

    private PlayerArrayAdapter mScorerAdapter;
    private PlayerArrayAdapter mAssistAdapter;
    private PlayerArrayAdapter mSecondaryAssistAdapter;
    private String mGameTime;

    public Player getScorer() {
        return mScorer;
    }

    public void setScorer(Player mScorer) {
        this.mScorer = mScorer;
    }

    public Player getAssist() {
        return mAssist;
    }

    public void setAssist(Player mAssist) {
        this.mAssist = mAssist;
    }

    public Player getSecondaryAssist() {
        return mSecondaryAssist;
    }

    public void setSecondaryAssist(Player mSecondaryAssist) {
        this.mSecondaryAssist = mSecondaryAssist;
    }

    public GoalViewModel(Application application) {
        super(application);
        HockeyDatabase db = HockeyDatabase.getDatabase(application);
        GameDao gameDao = db.getGameDao();
        GameScoringDao gameScoringDao = db.getGameScoringDao();
        GameShotsDao gameShotDao = db.getGameShotsDao();
        GameLineupDao gameLineupDao = db.getGameLineupDao();


        mRepository = new GameRepository(gameDao, gameScoringDao, gameShotDao);
        mGameLineupRepository = new GameLineupRepositoryImpl(gameLineupDao);
    }

    public void initialize(long gameId, int teamId, String homeOrAway, String gameTime, int currentPeriod) {
        mGameId = gameId;
        mTeamId = teamId;
        mHomeOrAway = homeOrAway;
        mGameTime = gameTime;
        mCurrentPeriod = currentPeriod;
    }

    public PlayerArrayAdapter getScorerAdapter() {
        return mScorerAdapter;
    }

    public void setScorerAdapter(PlayerArrayAdapter mScorerAdapter) {
        this.mScorerAdapter = mScorerAdapter;
    }

    public PlayerArrayAdapter getAssistAdapter() {
        return mAssistAdapter;
    }

    public void setAssistAdapter(PlayerArrayAdapter mAssistAdapter) {
        this.mAssistAdapter = mAssistAdapter;
    }

    public PlayerArrayAdapter getSecondaryAssistAdapter() {
        return mSecondaryAssistAdapter;
    }

    public void setSecondaryAssistAdapter(PlayerArrayAdapter mSecondaryAssistAdapter) {
        this.mSecondaryAssistAdapter = mSecondaryAssistAdapter;
    }

//    public Single<Long> addGoal(int scorerId, String timeOfGoal, int assistId, int secondaryAssistId) {
//        GameScoring goal = new GameScoring(mGameId, mTeamId, mHomeOrAway, mCurrentPeriod,
//                scorerId, timeOfGoal, assistId, secondaryAssistId);
//        return mRepository.addGoal(goal);
//        return mRepository.addGameScoring(goal);
//    }


    public LiveData<List<Player>> getActivePlayers() {
        return mGameLineupRepository.getActivePlayers(mGameId, mTeamId, mHomeOrAway);
    }

    public void onPlayerSelected(AdapterView<?> adapterView, Player player) {
        switch (adapterView.getId()){
            case R.id.scoring_player_spinner:
                setScorer(player);
                break;
            case R.id.assist_player_spinner:
                setAssist(player);
                break;
            case R.id.secondary_assist_spinner:
                setSecondaryAssist(player);
                break;

        }
    }

    public void savedGoal(GoalFragment goalFragment) {
        GameScoring scoring =
                new GameScoring(mGameId, mTeamId, mHomeOrAway, mCurrentPeriod, mScorer.mJerseyNumber,
                        mGameTime, mAssist.mJerseyNumber, mSecondaryAssist.mJerseyNumber);
        if (mHomeOrAway.toLowerCase().equals("home")) {
            ((PlayGameActivity) goalFragment.getActivity()).goalFragmentHomeScored(scoring);
            return;
        }

        if (mHomeOrAway.toLowerCase().equals("away")) {
            ((PlayGameActivity) goalFragment.getActivity()).goalFragmentHomeScored(scoring);
            return;
        }

        ((PlayGameActivity) goalFragment.getActivity()).goalFragmentCanceled();
    }
}
