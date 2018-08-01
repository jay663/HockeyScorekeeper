package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scoreit.hockeyscorekeeper.model.Coach;
import com.scoreit.hockeyscorekeeper.viewmodel.CoachViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.scoreit.hockeyscorekeeper.AddCoachActivity.ADD_COACH_TEAM_ID;

public class CoachesActivity extends AppCompatActivity {
    public static final String COACHES_TEAM_ID = "COACHES_TEAM_ID";
    public static final String COACHES_TEAM_NAME = "COACHES_TEAM_NAME";

    private CoachViewModel mCoachViewModel;
    private CoachListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private BottomNavigationView mBottomMenu;
    private int mTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mTeamId = intent.getIntExtra(COACHES_TEAM_ID, -1);
        String title = "Add Team Coaches";
        String teamName = intent.getStringExtra(COACHES_TEAM_NAME);
        if (teamName != null) {
            title = String.format("Add %s Coaches", teamName);
        }

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.coaches_recycler_view);
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.coaches_fab);
        mBottomMenu = (BottomNavigationView)findViewById(R.id.coaches_navigationView);

        mAdapter = new CoachListAdapter(this, mBottomMenu, addButton, mTeamId);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCoachViewModel = ViewModelProviders.of(this).get(CoachViewModel.class);

        mCoachViewModel.getTeamCoaches(mTeamId).observe(this, new Observer<List<Coach>>() {
            @Override
            public void onChanged(@Nullable final List<Coach> coaches) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setCoaches(coaches);
            }
        });

        initializeSwipe();

        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Coach coach = mAdapter.getSelectedCoach();
                if (coach != null) {
                    switch (item.getItemId()) {
                        case R.id.navigation_edit_coach:
//                            Intent editPlayerIntent = new Intent(getApplicationContext(), EditPlayerActivity.class);
//                            editPlayerIntent.putExtra(EDIT_TEAM_ID, coach.mTeamId);
//                            editPlayerIntent.putExtra(EDIT_PLAYER_ID, coach.mJerseyNumber);
//                            startActivity(editPlayerIntent);
//                            mAdapter.clearSelectedCoach();
                            return true;
                    }
                }
                return false;
            }
        });

    }

    private void initializeSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Coach coach = mAdapter.removeItem(position);
                mCoachViewModel.deleteCoach(coach);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void onAddCoachFloatBtnClick(View view) {
        Intent addCoachIntent = new Intent(getApplicationContext(), AddCoachActivity.class);
        addCoachIntent.putExtra(ADD_COACH_TEAM_ID, mTeamId);
        startActivity(addCoachIntent);
        mAdapter.clearSelectedCoach();
    }
}
