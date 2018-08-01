package com.scoreit.hockeyscorekeeper;

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

    private SelectTeamViewModel mSelectTeamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //EditText homeTeamLabel = (EditText) findViewById(R.id.home_team_selector_label);
        //EditText awayTeamLabel = (EditText) findViewById(R.id.away_team_selector_label);

        mSelectTeamViewModel = ViewModelProviders.of(this).get(SelectTeamViewModel.class);

        Spinner homeTeamSpinner = (Spinner) findViewById(R.id.home_team_selector);
        Spinner awayTeamSpinner = (Spinner) findViewById(R.id.away_team_selector);

        ArrayAdapter homeArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mSelectTeamViewModel.getAllTeams());

        ArrayAdapter awayArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mSelectTeamViewModel.getAllTeams());

        homeTeamSpinner.setAdapter(homeArrayAdapter);
        awayTeamSpinner.setAdapter(awayArrayAdapter);

    }

    public void OnNextFABClick(View view){
        Spinner homeTeamSpinner = (Spinner) findViewById(R.id.home_team_selector);
        Spinner awayTeamSpinner = (Spinner) findViewById(R.id.away_team_selector);
        Team selectedHomeTeam = (Team) homeTeamSpinner.getSelectedItem();
        Team selectedAwayTeam = (Team) awayTeamSpinner.getSelectedItem();

//        Intent scoreGameIntent = new Intent(getApplicationContext(), PlayGameActivity.class);
//        scoreGameIntent.putExtra("HOME_TEAM_ID", selectedHomeTeam.mTeamId);
//        scoreGameIntent.putExtra("AWAY_TEAM_ID", selectedAwayTeam.mTeamId);
//        startActivity(scoreGameIntent);
        finish();

    }

}
