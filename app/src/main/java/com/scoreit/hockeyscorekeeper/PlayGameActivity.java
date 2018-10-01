package com.scoreit.hockeyscorekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.scoreit.hockeyscorekeeper.databinding.ContentPlayGameBinding;
import com.scoreit.hockeyscorekeeper.model.GameScoring;
import com.scoreit.hockeyscorekeeper.viewmodel.GameVM;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.scoreit.hockeyscorekeeper.GoalFragment.ADD_GOAL_GAME_ID;
import static com.scoreit.hockeyscorekeeper.GoalFragment.ADD_GOAL_HOME_OR_AWAY_ID;
import static com.scoreit.hockeyscorekeeper.GoalFragment.ADD_GOAL_TEAM_ID;
import static com.scoreit.hockeyscorekeeper.GoalFragment.ADD_GOAL_TIME;

public class PlayGameActivity extends AppCompatActivity {
    public static final String GAME_ID = "GAME_ID";
    private final int PERIOD_TIME = 120000;
    private final int COUNTDOWN_INTERVAL = 1000;
    private long mGameId;
    public GameVM gameVM;

    private boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private boolean isCanceled = false;
    protected TextView tView;
    protected ImageButton btnStart;
    protected ImageButton btnPause;
    protected ImageButton btnResume;
    protected ImageButton btnCancel;
    protected CountDownTimer timer;
    protected HockeyGameClock gameClock;
    //long millisInFuture = 30000; //30 seconds
    protected long millisInFuture = PERIOD_TIME;
    protected long countDownInterval = COUNTDOWN_INTERVAL; //1 second

    //Declare a variable to hold CountDownTimer remaining time
    private long timeRemaining = 0;
    private Date dt = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Intent intent = getIntent();

        mGameId = intent.getLongExtra(GAME_ID, -1);
        gameVM = new GameVM(mGameId, getApplication());
        gameClock = new HockeyGameClock(PERIOD_TIME, COUNTDOWN_INTERVAL);

        gameClock.setTimeExpiredListener(new TimerExpiredListener() {
            @Override
            public void onTimerExpired(String source, boolean isManual) {
                onPeriodEnds(source, isManual);
            }
        });


        ContentPlayGameBinding binding = DataBindingUtil.setContentView(this, R.layout.content_play_game);
        binding.setVm(gameVM);
        binding.setClock(gameClock);

        tView = (TextView) findViewById(R.id.tv);
        btnStart = (ImageButton) findViewById(R.id.btn_start_timer);
        btnPause = (ImageButton) findViewById(R.id.btn_pause_timer);
        btnResume = (ImageButton) findViewById(R.id.btn_resume_timer);
        btnCancel = (ImageButton) findViewById(R.id.btn_stop_timer);

        //Initially disabled the pause, resume and cancel button
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnCancel.setEnabled(false);

        binding.setLifecycleOwner(this);
    }
    public void onHomeTeamScore(View view){
        gameClock.pause();
        String goalTime = gameClock.getClockDisplay();

        TextView textView = (TextView)findViewById(R.id.home_team);
        int teamId = (Integer)textView.getTag();

        OpenGoalDialog(goalTime, teamId, "home");

        // TODO: Add to Goal Fragment View Model
        //gameVM.homeTeamScored();
    }

    public void onAwayTeamScore(View view){
        gameClock.pause();
        String goalTime = gameClock.getClockDisplay();

        TextView textView = (TextView)findViewById(R.id.away_team);
        int teamId = (Integer)textView.getTag();

        OpenGoalDialog(goalTime, teamId, "away");
    }

    protected void OpenGoalDialog(String goalTime, int teamId, String away) {
        Bundle bundle = createGoalBundle(goalTime, teamId, away);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment previousDialog = getSupportFragmentManager().findFragmentByTag("goalFragment");
        if (previousDialog != null) {
            transaction.remove(previousDialog);
        }
        transaction.addToBackStack(null);

        GoalFragment fragment = new GoalFragment();
        fragment.setArguments(bundle);
        transaction.add(android.R.id.content, fragment, "goalFragment");
        transaction.commit();
    }

    protected Bundle createGoalBundle(String goalTime, int teamId, String homeOrAway) {
        homeOrAway = homeOrAway.toLowerCase();
        Bundle bundle = new Bundle();
        bundle.putLong(ADD_GOAL_GAME_ID, mGameId);
        bundle.putInt(ADD_GOAL_TEAM_ID, teamId);
        bundle.putString(ADD_GOAL_HOME_OR_AWAY_ID, homeOrAway);
        bundle.putString(ADD_GOAL_TIME, goalTime);
        return bundle;
    }

    public void onHomeTeamShot(View view){
        gameVM.addHomeTeamShot();
    }

    public void onAwayTeamShot(View view){
        gameVM.addAwayTeamShot();
    }

    public void onStartTimerClicked(View view){
        isPaused = false;
        isCanceled = false;

        //Disable the start and pause button
        btnStart.setEnabled(false);
        btnResume.setEnabled(false);
        //Enabled the pause and cancel button
        btnPause.setEnabled(true);
        btnCancel.setEnabled(true);

        millisInFuture = PERIOD_TIME;
        countDownInterval = COUNTDOWN_INTERVAL;

        gameClock.start();
    }

    public void onUpMinuteClicked(View view){
        if (!gameClock.addMinute()){
            Toast.makeText(this, "Game clock must be paused to adjust timer", Toast.LENGTH_SHORT).show();
        }

    }

    public void onUpSecondClicked(View view){
        if (!gameClock.addSecond())
        {
            Toast.makeText(this, "Game clock must be paused to adjust timer", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDownMinuteClicked(View view){
        if (!gameClock.minusMinute())
        {
            Toast.makeText(this, "Game clock must be paused to adjust timer", Toast.LENGTH_SHORT).show();
        }

    }

    public void onDownSecondClicked(View view){
        if (!gameClock.minusSecond())
        {
            Toast.makeText(this, "Game clock must be paused to adjust timer", Toast.LENGTH_SHORT).show();
        }

    }

    public void onStopTimerClicked(View view){
        //isCanceled = true;
        gameClock.stop();

        //Disable the cancel, pause and resume button
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnCancel.setEnabled(false);
        //Enable the start button
        btnStart.setEnabled(true);

        //Notify the user that CountDownTimer is canceled/stopped
        tView.setText("00:00");
    }

    public void onPauseTimerClicked(View view){
        //isPaused = true;
        gameClock.pause();

        //Enable the resume and cancel button
        btnResume.setEnabled(true);
        btnCancel.setEnabled(true);
        //Disable the start and pause button
        btnStart.setEnabled(false);
        btnPause.setEnabled(false);
    }

    public void onResumeTimerClicked(View view){
        //Disable the start and resume button
        btnStart.setEnabled(false);
        btnResume.setEnabled(false);
        //Enable the pause and cancel button
        btnPause.setEnabled(true);
        btnCancel.setEnabled(true);

        gameClock.resume();
    }

    public void onPeriodEnds(String source, Boolean isManual){
        // When period ends update period, reset timer, and pause clock
        if (!gameVM.isGameOver())
        {
            gameVM.addPeriod();
            this.gameClock.restart();

        }else {
            tView.setText("00:00");
        }

        //Enable the start button
        btnStart.setEnabled(true);
        //Disable the pause, resume and cancel button
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnCancel.setEnabled(false);
    }

    /**
     * Goal Fragment Methods
     */
    public void goalFragmentAwayScored(GameScoring scoring){
        clearGoalFragment();
        gameVM.awayTeamScored(scoring);
    }

    public void goalFragmentHomeScored(GameScoring scoring){
        clearGoalFragment();
        gameVM.homeTeamScored(scoring);
    }


    public void goalFragmentCanceled(){
        clearGoalFragment();
    }

    protected void clearGoalFragment(){
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            if (fragment!=null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == Activity.RESULT_OK) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get goal data from Intent
                //Goal goal = (Goal) data.getExtras().getSerializable("GOAL_DETAILS");
            }
        }
    }

}
