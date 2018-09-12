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
import com.scoreit.hockeyscorekeeper.model.Goal;
import com.scoreit.hockeyscorekeeper.viewmodel.GameVM;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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

        ContentPlayGameBinding binding = DataBindingUtil.setContentView(this, R.layout.content_play_game);
        binding.setStuff(gameVM);
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
    }
    public void onHomeTeamScore(View view){
        gameVM.homeTeamScored();
//        int goals = 0;
//
//        switch (gameVM.game.getValue().currentPeriod){
//            case 1:
//                goals = gameVM.game.getValue().scoreboard.homePeriod1Goals + 1;
//                gameVM.game.getValue().scoreboard.homePeriod1Goals = goals;
//                break;
//            case 2:
//                goals = gameVM.game.getValue().scoreboard.homePeriod2Goals + 1;
//                gameVM.game.getValue().scoreboard.homePeriod2Goals = goals;
//                break;
//            case 3:
//                goals = gameVM.game.getValue().scoreboard.homePeriod3Goals + 1;
//                gameVM.game.getValue().scoreboard.homePeriod3Goals = goals;
//                break;
//            case 4:
//                goals = gameVM.game.getValue().scoreboard.homeOTGoals + 1;
//                gameVM.game.getValue().scoreboard.homeOTGoals = goals;
//                break;
//        }
//
//        goals = gameVM.game.getValue().scoreboard.homeFinalScore + 1;
//        gameVM.game.getValue().scoreboard.homeFinalScore = goals;

    }

    public void onAwayTeamScore(View view){
        int goals = 0;

        gameVM.awayTeamScored();

        switch (gameVM.game.getValue().currentPeriod){
            case 1:
                goals = gameVM.game.getValue().scoreboard.awayPeriod1Goals + 1;
                gameVM.game.getValue().scoreboard.awayPeriod1Goals = goals;
                break;
            case 2:
                goals = goals = gameVM.game.getValue().scoreboard.awayPeriod2Goals + 1;
                gameVM.game.getValue().scoreboard.awayPeriod2Goals = goals;
                break;
            case 3:
                goals = goals = gameVM.game.getValue().scoreboard.awayPeriod3Goals + 1;
                gameVM.game.getValue().scoreboard.awayPeriod3Goals = goals;
                break;
            case 4:
                goals = goals = gameVM.game.getValue().scoreboard.awayOTGoals + 1;
                gameVM.game.getValue().scoreboard.awayOTGoals = goals;
                break;
        }

        goals = gameVM.game.getValue().scoreboard.awayFinalScore + 1;
        gameVM.game.getValue().scoreboard.awayFinalScore = goals;

    }

    public void onHomeTeamShot(View view){
//        int x = gameVM.shots.getValue().homePeriod1Shots + 1;
//        gameVM.shots.getValue().homePeriod1Shots = x;
//
//        x = gameVM.shots.getValue().homeShotTotal + 1;
//        gameVM.shots.getValue().homeShotTotal = x;
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

    public void onPeriodEnds(){
        // When period ends update period, reset timer, and pause clock
        if (!isGameFinished())
        {
            int nextPeriod = gameVM.game.getValue().currentPeriod + 1;
            gameVM.game.getValue().currentPeriod = nextPeriod;
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

    protected boolean isGameFinished(){
//        int currentPeriod = gameVM.game.getValue().currentPeriod;
//        int homeScore = gameVM.game.getValue().scoreboard.homeFinalScore;
//        int awayScore = gameVM.game.getValue().scoreboard.awayFinalScore;
//
//        // Game Finishes after 3 periods and there is no tie
//        if (currentPeriod == 3 && (homeScore != awayScore)){
//            return true;
//        }
//
//        return false;
        return gameVM.isGameFinished();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == Activity.RESULT_OK) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get goal data from Intent
                Goal goal = (Goal) data.getExtras().getSerializable("GOAL_DETAILS");
            }
        }
    }
}
