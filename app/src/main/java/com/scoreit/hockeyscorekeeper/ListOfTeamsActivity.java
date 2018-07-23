package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.scoreit.hockeyscorekeeper.AddTeamActivity.EXTRA_PLAYER_TEAM_ID;
import static com.scoreit.hockeyscorekeeper.EditTeamActivity.EDIT_TEAM_ID;

public class ListOfTeamsActivity extends AppCompatActivity {
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
        //android.support.design.bottomappbar.BottomAppBar
        setSupportActionBar(toolbar);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        mBottomMenu = (BottomNavigationView)findViewById(R.id.navigationView);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new TeamListAdapter(this, mBottomMenu, button);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeSwipe();

        mTeamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        mTeamViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable final List<Team> teams) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setTeams(teams);
            }
        });

        //mBottomMenu.bringToFront();
        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Team team = mAdapter.getSelectedTeam();
                if (team != null) {
                    switch (item.getItemId()) {
                        case R.id.navigation_edit_team:
                            Intent editTeamIntent = new Intent(getApplicationContext(), EditTeamActivity.class);
                            editTeamIntent.putExtra(EDIT_TEAM_ID, team.mTeamId);
                            startActivity(editTeamIntent);
                            return true;
                        case R.id.navigation_manage_roster:
                            Intent teamRosterIntent = new Intent(getApplicationContext(), TeamRosterActivity.class);
                            teamRosterIntent.putExtra(EXTRA_PLAYER_TEAM_ID, team.mTeamId);
                            startActivity(teamRosterIntent);

                            return true;
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
                mTeamViewModel.deleteTeam(team);
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
