package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.viewmodel.PlayerViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.scoreit.hockeyscorekeeper.AddTeamActivity.EXTRA_PLAYER_TEAM_ID;

public class TeamRosterActivity extends AppCompatActivity {

    public static final String ADD_PLAYER_TEAM_ID = "ADD_PLAYER_TEAM_ID";
    private PlayerViewModel mPlayerViewModel;
    private int mTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_roster);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mTeamId = intent.getIntExtra(EXTRA_PLAYER_TEAM_ID, -1);

        RecyclerView recyclerView = findViewById(R.id.player_recycler_view);
        final PlayerListAdapter adapter = new PlayerListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mPlayerViewModel.getTeamPlayers(mTeamId).observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable final List<Player> players) {
                // Update the cached copy of the words in the adapter.
                adapter.setPlayers(players);
            }
        });
    }

    public void onAddPlayerFloatBtnClick(View view){
        Intent addPlayerIntent = new Intent(getApplicationContext(), AddPlayerActivity.class);
        addPlayerIntent.putExtra(ADD_PLAYER_TEAM_ID, mTeamId);
        startActivity(addPlayerIntent);
    }


}
