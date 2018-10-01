package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.viewmodel.PlayerViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import static com.scoreit.hockeyscorekeeper.TeamRosterActivity.ADD_PLAYER_TEAM_ID;

public class AddPlayerActivity extends AppCompatActivity {
    private PlayerViewModel mPlayerViewModel;
    private EditText mPlayerNameTextBox;
    private EditText mPlayerJerseyTextBox;
    private Spinner mPlayerPosSpinner;
    private int mTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mTeamId = intent.getIntExtra(ADD_PLAYER_TEAM_ID, -1);
        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        mPlayerNameTextBox = findViewById(R.id.add_player_name_et);
        mPlayerJerseyTextBox = findViewById(R.id.add_player_jersey_number_et);
        mPlayerPosSpinner = (Spinner) findViewById(R.id.add_player_position_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.position_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlayerPosSpinner.setAdapter(adapter);

    }

    public void onAddPlayerBtnClick(View view){
        Intent replyIntent = new Intent();

        if (validPlayerInput()) {
            String playerName = mPlayerNameTextBox.getText().toString();
            String position = mPlayerPosSpinner.getSelectedItem().toString();
            int jersey = Integer.parseInt(mPlayerJerseyTextBox.getText().toString());

            Player player = new Player(jersey, mTeamId, playerName, position);
            mPlayerViewModel.insert(player);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    private boolean validPlayerInput() {
        if(TextUtils.isEmpty(mPlayerJerseyTextBox.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter jersey number", Toast.LENGTH_SHORT).show();
            return false;
        }
        String jersey = mPlayerJerseyTextBox.getText().toString();
        if(!TextUtils.isDigitsOnly(jersey)) {
            Toast.makeText(getApplicationContext(), "Please enter jersey number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(mPlayerNameTextBox.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter player name", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void onCancelBtnClick(View view){
        finish();
    }
}
