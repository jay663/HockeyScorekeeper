package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.viewmodel.TeamViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


public class AddTeamActivity extends AppCompatActivity implements IAsyncTaskResults<Long> {

    public static final String EXTRA_PLAYER_TEAM_ID = "PLAYER_TEAM_ID";
    public static final String EXTRA_REPLY = "com.scoreit.hockeyscorekeeper.REPLY";

    private TeamViewModel mTeamViewModel;
    private EditText mEditTeamName;
    private EditText mEditTeamLocation;
    private EditText mEditTeamMascot;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        setContentView(R.layout.activity_add_team);
        mEditTeamName = findViewById(R.id.edit_team_name);
        mEditTeamLocation = findViewById(R.id.edit_team_location);
        mEditTeamMascot = findViewById(R.id.edit_team_mascot);
    }

    public void onAddTeamClick(View view){
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditTeamName.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String teamName = mEditTeamName.getText().toString();
            String teamLocation = mEditTeamLocation.getText().toString();
            String teamMascot = mEditTeamMascot.getText().toString();
            addTeam(teamName, teamLocation, teamMascot);
            setResult(RESULT_OK, replyIntent);
        }
    }

    public void onAddTeamCancelClick(View view){
        finish();
    }

    protected void addTeam(String teamName, String location, String mascot){
        Team team = new Team(teamName, location, mascot);
        mTeamViewModel.insert(team, this);
    }


    @Override
    public void onItemAdded(Long teamId) {
        int id = teamId.intValue();
        mTeamViewModel.removeListener();
        Intent teamRosterIntent = new Intent(getApplicationContext(), TeamRosterActivity.class);
        teamRosterIntent.putExtra(EXTRA_PLAYER_TEAM_ID, id);
        startActivity(teamRosterIntent);
        finish();
    }

    @Override
    public void onDestroy() {
        mTeamViewModel.removeListener();
        super.onDestroy();
    }
}
