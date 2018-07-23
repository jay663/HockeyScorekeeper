package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.viewmodel.TeamViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private TeamViewModel mTeamViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
    }

    protected void onStartGameBtnClick(View view){
        // Game needs 2 teams to start
        List<Team> teams = mTeamViewModel.getAllTeams().getValue();

        startActivity(new Intent(getApplicationContext(), SelectTeamsActivity.class));

//        if (teams == null || teams.size() < 2){
//            startActivity(new Intent(getApplicationContext(), ListOfTeamsActivity.class));
//        }else {
//            startActivity(new Intent(getApplicationContext(), SelectTeamsActivity.class));
//        }

        /**
         *  Steps To Game:
         *  Select Teams
         *  Set Rosters
         *  Set 
         **/
    }

    protected void onAddTeamBtnClick(View view){
        startActivity(new Intent(getApplicationContext(), ListOfTeamsActivity.class));

    }

}
