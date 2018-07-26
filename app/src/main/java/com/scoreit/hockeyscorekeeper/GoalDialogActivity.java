package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scoreit.hockeyscorekeeper.model.Goal;
import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.viewmodel.PlayerViewModel;
import com.scoreit.hockeyscorekeeper.viewmodel.TeamViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class GoalDialogActivity extends AppCompatActivity {
    private int mTeamId;
    private PlayerViewModel mPlayerViewModel;
    private TeamViewModel mTeamViewModel;
    private Team mTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_dialog);

        Intent intent = getIntent();
        mTeamId = intent.getIntExtra("TEAM", -1);

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        mTeamViewModel.getTeam(mTeamId);
    }

    public void onSaveGoal(View view)
    {
        Goal goal = new Goal();


        Intent output = new Intent();
        output.putExtra("GOAL", goal);
        setResult(RESULT_OK, output);
        finish();
    }

    private void loadTeam(int teamId) {
        mTeamViewModel.getTeam(teamId).observe(this, new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                loadTeamEditFields(team);
            }
        });

        return;
    }

    protected void loadTeamEditFields(Team team){
        mTeam = team;
    }

//    private Team getTeam(int teamId) {
//        final Team[] resultTeam = {null};
//        mTeamViewModel.getTeam(teamId).observe(this, new Observer<Team>() {
//            @Override
//            public void onChanged(Team team) {
//                resultTeam[0] = team;
//            }
//        });
//        return resultTeam[0];
//    }



}
