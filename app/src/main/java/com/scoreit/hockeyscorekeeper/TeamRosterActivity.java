package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.viewmodel.PlayerViewModel;

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

import static com.scoreit.hockeyscorekeeper.AddTeamActivity.EXTRA_PLAYER_TEAM_ID;
import static com.scoreit.hockeyscorekeeper.EditPlayerActivity.EDIT_PLAYER_ID;
import static com.scoreit.hockeyscorekeeper.EditTeamActivity.EDIT_TEAM_ID;

public class TeamRosterActivity extends AppCompatActivity {

    public static final String ADD_PLAYER_TEAM_ID = "ADD_PLAYER_TEAM_ID";
    private static final String ADD_COACH_TEAM_ID = "ADD_COACH_TEAM_ID";
    private PlayerViewModel mPlayerViewModel;
    private PlayerListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private BottomNavigationView mBottomMenu;
    private int mTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_roster);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        mTeamId = intent.getIntExtra(EXTRA_PLAYER_TEAM_ID, -1);
        String title = "Manage Roster";
        String teamName = intent.getStringExtra(ListOfTeamsActivity.ROSTER_TEAM_NAME);
        if (teamName != null) {
            title = String.format("%s Roster", teamName);
        }

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.player_recycler_view);
        FloatingActionButton addButton = findViewById(R.id.fab);
        mBottomMenu = findViewById(R.id.roster_navigationView);
        mAdapter = new PlayerListAdapter(this, mBottomMenu, addButton, mTeamId);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mPlayerViewModel.getTeamPlayers(mTeamId).observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable final List<Player> players) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setPlayers(players);
            }
        });

        initializeSwipe();

        mBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Player player = mAdapter.getSelectedPlayer();
                if (player != null) {
                    switch (item.getItemId()) {
                        case R.id.navigation_edit_player:
                            Intent editPlayerIntent = new Intent(getApplicationContext(), EditPlayerActivity.class);
                            editPlayerIntent.putExtra(EDIT_TEAM_ID, player.mTeamId);
                            editPlayerIntent.putExtra(EDIT_PLAYER_ID, player.mJerseyNumber);
                            startActivity(editPlayerIntent);
                            mAdapter.clearSelectedPlayer();
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
                Player player = mAdapter.removeItem(position);
                mPlayerViewModel.deletePlayer(player);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void onAddPlayerFloatBtnClick(View view){
        Intent addPlayerIntent = new Intent(getApplicationContext(), AddPlayerActivity.class);
        addPlayerIntent.putExtra(ADD_PLAYER_TEAM_ID, mTeamId);
        startActivity(addPlayerIntent);
    }

}
