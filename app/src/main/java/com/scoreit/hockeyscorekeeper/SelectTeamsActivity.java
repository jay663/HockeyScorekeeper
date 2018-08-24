package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.viewmodel.SelectTeamViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class SelectTeamsActivity extends AppCompatActivity {
    public static final String HOME_TEAM_ID = "HOME_TEAM_ID";
    public static final String AWAY_TEAM_ID = "AWAY_TEAM_ID";

    private SelectTeamViewModel mViewModel;
    private ArrayAdapter<Team> mHomeArrayAdapter;
    private ArrayAdapter<Team> mAwayArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewModel = ViewModelProviders.of(this).get(SelectTeamViewModel.class);

        Spinner homeTeamSpinner = (Spinner) findViewById(R.id.home_team_selector);
        Spinner awayTeamSpinner = (Spinner) findViewById(R.id.away_team_selector);

        mViewModel.getAllTeams().observe(this, teamList -> {
            mHomeArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, teamList);

            mAwayArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, teamList);

            homeTeamSpinner.setAdapter(mHomeArrayAdapter);
            awayTeamSpinner.setAdapter(mAwayArrayAdapter);
        });

    }

    public void OnNextFABClick(View view){
        Spinner homeTeamSpinner = (Spinner) findViewById(R.id.home_team_selector);
        Spinner awayTeamSpinner = findViewById(R.id.away_team_selector);
        Team selectedHomeTeam = (Team) homeTeamSpinner.getSelectedItem();
        Team selectedAwayTeam = (Team) awayTeamSpinner.getSelectedItem();

        Intent setLineupIntent = new Intent(getApplicationContext(), SetLineupActivity.class);
        setLineupIntent.putExtra(HOME_TEAM_ID, selectedHomeTeam.mTeamId);
        setLineupIntent.putExtra(AWAY_TEAM_ID, -1);
        startActivity(setLineupIntent);

        setLineupIntent = new Intent(getApplicationContext(), SetLineupActivity.class);
        setLineupIntent.putExtra(HOME_TEAM_ID, -1);
        setLineupIntent.putExtra(AWAY_TEAM_ID, selectedAwayTeam.mTeamId);
        startActivity(setLineupIntent);

        // Set Home Team Lineup -> Set Away Team Lineup
        // Start Game


    }

}
