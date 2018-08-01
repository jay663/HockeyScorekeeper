package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.viewmodel.PlayerViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.scoreit.hockeyscorekeeper.EditTeamActivity.EDIT_TEAM_ID;

public class EditPlayerActivity extends AppCompatActivity {

    public static final String EDIT_PLAYER_ID = "EDIT_PLAYER_ID";

    private PlayerViewModel mPlayerViewModel;
    private EditText mEditPlayerName;
    private TextView mEditPlayerJersyLabel;
    private Spinner mEditPlayerPositionSpinner;
    private Player mOriginalPlayer;
    private Player mUpdatedPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int teamId = intent.getIntExtra(EDIT_TEAM_ID, -1);
        int playerId = intent.getIntExtra(EDIT_PLAYER_ID, -1);

        mEditPlayerJersyLabel = findViewById(R.id.edit_player_jersey_number);
        mEditPlayerName = findViewById(R.id.edit_player_name);
        mEditPlayerPositionSpinner = (Spinner) findViewById(R.id.edit_player_spinner_position);
        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        loadTeam(teamId, playerId);
    }

    private void loadTeam(int teamId, int playerId) {
        mPlayerViewModel.getPlayer(teamId, playerId).observe(this, new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                loadPlayerEditFields(player);
            }
        });

        return;
    }

    private void loadPlayerEditFields(Player player) {
        mOriginalPlayer = player;
        mEditPlayerJersyLabel.setText(String.valueOf(player.mJerseyNumber));
        mEditPlayerName.setText(player.mPlayerName);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.position_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditPlayerPositionSpinner.setAdapter(adapter);

        int selectionPosition= adapter.getPosition(player.mPosition);
        mEditPlayerPositionSpinner.setSelection(selectionPosition);
    }

    public void onEditPlayerSaveTeamClick(View view) {
        mUpdatedPlayer = mOriginalPlayer;

        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditPlayerName.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String playerName = mEditPlayerName.getText().toString();
            String playerPosition = mEditPlayerPositionSpinner.getSelectedItem().toString();

            mUpdatedPlayer.mPlayerName = playerName;
            mUpdatedPlayer.mPosition = playerPosition;
            mPlayerViewModel.update(mUpdatedPlayer);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    public void onEditPlayerCancelClick(View view) {
        finish();
    }
}
