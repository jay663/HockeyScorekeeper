package com.scoreit.hockeyscorekeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.scoreit.hockeyscorekeeper.model.Team;
import com.scoreit.hockeyscorekeeper.viewmodel.TeamViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.scoreit.hockeyscorekeeper.AddTeamActivity.EXTRA_PLAYER_TEAM_ID;
import static com.scoreit.hockeyscorekeeper.CoachesActivity.COACHES_TEAM_ID;
import static com.scoreit.hockeyscorekeeper.CoachesActivity.COACHES_TEAM_NAME;
import static com.scoreit.hockeyscorekeeper.EditTeamActivity.EDIT_TEAM_ID;

public class ListOfTeamsActivity extends AppCompatActivity {
    public static final String ROSTER_TEAM_NAME = "ROSTER_TEAM_NAME";

    private TeamViewModel mTeamViewModel;
    private TeamListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private BottomNavigationView mBottomMenu;
    private NavigationView.OnNavigationItemSelectedListener mItemSelectedListener;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        mBottomMenu = (BottomNavigationView)findViewById(R.id.navigationView);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TeamListAdapter(this, mBottomMenu, button);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        mTeamViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable final List<Team> teams) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setTeams(teams);
            }
        });

        initializeSwipe();

        //mBottomMenu.bringToFront();
        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Team team = mAdapter.getSelectedTeam();
                if (team != null) {
                    String displayTeamName = String.format("%s %s", team.mLocation, team.mTeamName);
                    switch (item.getItemId()) {
                        case R.id.navigation_edit_team:
                            Intent editTeamIntent = new Intent(getApplicationContext(), EditTeamActivity.class);
                            editTeamIntent.putExtra(EDIT_TEAM_ID, team.mTeamId);
                            startActivity(editTeamIntent);
                            mAdapter.clearSelectedTeam();
                            return true;
                        case R.id.navigation_manage_roster:
                            Intent teamRosterIntent = new Intent(getApplicationContext(), TeamRosterActivity.class);
                            teamRosterIntent.putExtra(EXTRA_PLAYER_TEAM_ID, team.mTeamId);
                            teamRosterIntent.putExtra(ROSTER_TEAM_NAME, displayTeamName);
                            startActivity(teamRosterIntent);
                            mAdapter.clearSelectedTeam();
                            return true;
                        case R.id.navigation_add_coaches:
                            Intent addCoachIntent = new Intent(getApplicationContext(), CoachesActivity.class);
                            addCoachIntent.putExtra(COACHES_TEAM_ID, team.mTeamId);
                            addCoachIntent.putExtra(COACHES_TEAM_NAME, displayTeamName);
                            startActivity(addCoachIntent);
                            mAdapter.clearSelectedTeam();
                    }
                }
                return false;
            }
        });
    }


    protected void initializeSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Team team = mAdapter.removeItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ListOfTeamsActivity.this);
                builder.setTitle("Delete Team");
                builder.setMessage("Deleting a team will remove the team and every player on the team. Are you sure you want to delete this team?");
                builder.setPositiveButton("Delete Team", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTeamViewModel.deleteTeam(team);
                        //go to update activity
                        //goToUpdateActivity(person.getId());
                        Log.d(TAG, "Team Deleted");

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.restoreItem(team, position);
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void onAddTeamFloatBtnClick(View view){
        startActivity(new Intent(getApplicationContext(), AddTeamActivity.class));
    }

}
