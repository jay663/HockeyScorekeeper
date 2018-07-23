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

public class EditTeamActivity extends AppCompatActivity {
    public static final String EDIT_TEAM_ID = "EDIT_TEAM_ID";
    public static final String EXTRA_REPLY = "com.scoreit.hockeyscorekeeper.REPLY";

    private TeamViewModel mTeamViewModel;
    private EditText mEditTeamName;
    private EditText mEditTeamLocation;
    private EditText mEditTeamMascot;
    private Team mOriginalTeam;
    private Team mUpdatedTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        Intent intent = getIntent();
        int teamId = intent.getIntExtra(EDIT_TEAM_ID, -1);
        mOriginalTeam = mTeamViewModel.getTeam(teamId);

        mEditTeamName = findViewById(R.id.edit_activity_team_name);
        mEditTeamName.setText(mOriginalTeam.mTeamName);

        mEditTeamLocation = findViewById(R.id.edit_activity_team_location);
        mEditTeamName.setText(mOriginalTeam.mLocation);

        mEditTeamMascot = findViewById(R.id.edit_activity_team_mascot);
        mEditTeamName.setText(mOriginalTeam.mMascott);

    }

    protected void onCancelClick(View view){
        finish();
    }

    protected void onSaveTeamClick(View view){
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditTeamName.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String teamName = mEditTeamName.getText().toString();
            String teamLocation = mEditTeamLocation.getText().toString();
            String teamMascot = mEditTeamMascot.getText().toString();
            mUpdatedTeam = new Team(teamName, teamLocation, teamMascot);
            mTeamViewModel.editTeam(mUpdatedTeam);
        }

    }

}
