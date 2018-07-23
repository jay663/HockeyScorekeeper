package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scoreit.hockeyscorekeeper.model.Goal;
import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.viewmodel.PlayerViewModel;
import com.scoreit.hockeyscorekeeper.viewmodel.TeamViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class GoalDialogActivity extends AppCompatActivity {
    private int mTeamId;
    private PlayerViewModel mPlayerViewModel;
    private TeamViewModel mTeamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_dialog);

        Intent intent = getIntent();
        mTeamId = intent.getIntExtra("TEAM", -1);

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        Team team = mTeamViewModel.getTeam(mTeamId);
    }

    public void onSaveGoal(View view)
    {
        Goal goal = new Goal();


        Intent output = new Intent();
        output.putExtra("GOAL", goal);
        setResult(RESULT_OK, output);
        finish();
    }


}
