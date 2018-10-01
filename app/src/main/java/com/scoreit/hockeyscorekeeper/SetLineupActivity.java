package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.scoreit.hockeyscorekeeper.adapters.PlayerArrayAdapter;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.viewmodel.SetLineupViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import static com.scoreit.hockeyscorekeeper.SelectTeamsActivity.AWAY_TEAM_ID;
import static com.scoreit.hockeyscorekeeper.SelectTeamsActivity.GAME_ID;
import static com.scoreit.hockeyscorekeeper.SelectTeamsActivity.HOME_TEAM_ID;

public class SetLineupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SetLineupViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lineup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int homeTeamId = intent.getIntExtra(HOME_TEAM_ID, -1);
        int awayTeamId = intent.getIntExtra(AWAY_TEAM_ID, -1);
        long gameId = intent.getLongExtra(GAME_ID, -1);

        mViewModel = ViewModelProviders.of(this).get(SetLineupViewModel.class);
        mViewModel.initialize(homeTeamId, awayTeamId, gameId);

        Spinner lwSpinner = (Spinner) findViewById(R.id.set_lineup_lw_spinner);
        Spinner cSpinner = (Spinner) findViewById(R.id.set_lineup_c_spinner);
        Spinner rwSpinner = (Spinner) findViewById(R.id.set_lineup_rw_spinner);
        Spinner ldSpinner = (Spinner) findViewById(R.id.set_lineup_leftd_spinner);
        Spinner rdSpinner = (Spinner) findViewById(R.id.set_lineup_rightd_spinner);
        Spinner goalieSpinner = (Spinner) findViewById(R.id.set_lineup_goalie_spinner);

        //Filter filter = mGArrayAdapter.getFilter();

        mViewModel.getTeamPlayers().observe(this, playerList -> {
            mViewModel.setLWArrayAdapter(new PlayerArrayAdapter(this,
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setCArrayAdapter(new PlayerArrayAdapter(this,
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setRWArrayAdapter(new PlayerArrayAdapter(this,
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setLDArrayAdapter(new PlayerArrayAdapter(this,
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setRDArrayAdapter(new PlayerArrayAdapter(this,
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setGArrayAdapter(new PlayerArrayAdapter(this,
                    R.layout.player_spinner_layout, playerList));


            lwSpinner.setAdapter(mViewModel.getLWArrayAdapter());
            cSpinner.setAdapter(mViewModel.getCArrayAdapter());
            rwSpinner.setAdapter(mViewModel.getRWArrayAdapter());
            ldSpinner.setAdapter(mViewModel.getLDArrayAdapter());
            rdSpinner.setAdapter(mViewModel.getRDArrayAdapter());
            goalieSpinner.setAdapter(mViewModel.getGArrayAdapter());
        });


        lwSpinner.setOnItemSelectedListener(this);
        cSpinner.setOnItemSelectedListener(this);
        rwSpinner.setOnItemSelectedListener(this);
        ldSpinner.setOnItemSelectedListener(this);
        rdSpinner.setOnItemSelectedListener(this);
        goalieSpinner.setOnItemSelectedListener(this);

        mViewModel.getTeam().observe(this, team -> {
            toolbar.setTitle(team.mTeamName);
        });

    }

    public void onSetLineupNextBtn(View view) {

        if(isValid()){

            mViewModel.saveLineup();
            if (mViewModel.isSettingHomeLineup())
            {
                Intent setLineupIntent = new Intent(getApplicationContext(), SetLineupActivity.class);
                setLineupIntent = new Intent(getApplicationContext(), SetLineupActivity.class);
                setLineupIntent.putExtra(HOME_TEAM_ID, -1);
                setLineupIntent.putExtra(AWAY_TEAM_ID, mViewModel.getAwayTeamId());
                setLineupIntent.putExtra(GAME_ID, mViewModel.getGameId());
                startActivity(setLineupIntent);
            }else {
                // Start Game Activity
                Intent playGameIntent = new Intent(getApplicationContext(), PlayGameActivity.class);
                playGameIntent.putExtra(GAME_ID, mViewModel.getGameId());
                startActivity(playGameIntent);
            }

            //finish();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        /**
         * When an item is selected I want to inform the ViewModel that it's time to update all our
         * dropdown lists.
         *
         *  I need to do the following. Determine the hockey position adjusted.
         *  I neeed to know the player selected
         *  Then I need to filter all the other adapters
         *  I might have to fire some notifyer for the UI to know it's adjusted
         *  Now what needs to be passed
         *  Player, adapterview
         */

        Player player = (Player) adapterView.getSelectedItem();
        mViewModel.onPlayerSelected(adapterView, player);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public boolean isValid(){
        String errorMsg = "";
        boolean isValid = false;
        Player lw = mViewModel.getLW();
        Player center = mViewModel.getCenter();
        Player rw = mViewModel.getRW();
        Player ld = mViewModel.getLD();
        Player rd = mViewModel.getRD();
        Player goalie = mViewModel.getGoalie();

        if (lw.mJerseyNumber == center.mJerseyNumber || lw.mJerseyNumber == rw.mJerseyNumber ||
                lw.mJerseyNumber == ld.mJerseyNumber || lw.mJerseyNumber == rd.mJerseyNumber ||
                lw.mJerseyNumber == goalie.mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as a left wing. He can't play multiple positions at the same time", lw.mPlayerName);
            Toast.makeText(this.getBaseContext(),errorMsg, Toast.LENGTH_LONG).show();
            return isValid;

        }

        if (center.mJerseyNumber == lw.mJerseyNumber || center.mJerseyNumber == rw.mJerseyNumber ||
                center.mJerseyNumber == ld.mJerseyNumber || center.mJerseyNumber == rd.mJerseyNumber ||
                center.mJerseyNumber == goalie.mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as a center. He can't play multiple positions at the same time", center.mPlayerName);
            Toast.makeText(this.getBaseContext(),errorMsg, Toast.LENGTH_LONG).show();
            return isValid;
        }

        if (rw.mJerseyNumber == lw.mJerseyNumber || rw.mJerseyNumber == center.mJerseyNumber ||
                rw.mJerseyNumber == ld.mJerseyNumber || rw.mJerseyNumber == rd.mJerseyNumber ||
                rw.mJerseyNumber == goalie.mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as a right wing. He can't play multiple positions at the same time", rw.mPlayerName);
            Toast.makeText(this.getBaseContext(),errorMsg, Toast.LENGTH_LONG).show();
            return isValid;
        }

        if (ld.mJerseyNumber == lw.mJerseyNumber || ld.mJerseyNumber == center.mJerseyNumber ||
                ld.mJerseyNumber == rw.mJerseyNumber || ld.mJerseyNumber == rd.mJerseyNumber ||
                ld.mJerseyNumber == goalie.mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as a left defenseman. He can't play multiple positions at the same time", ld.mPlayerName);
            Toast.makeText(this.getBaseContext(),errorMsg, Toast.LENGTH_LONG).show();
            return isValid;
        }

        if (rd.mJerseyNumber == lw.mJerseyNumber || rd.mJerseyNumber == center.mJerseyNumber ||
                rd.mJerseyNumber == rw.mJerseyNumber || rd.mJerseyNumber == ld.mJerseyNumber ||
                rd.mJerseyNumber == goalie.mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as a right defenseman. He can't play multiple positions at the same time", rd.mJerseyNumber);
            Toast.makeText(this.getBaseContext(),errorMsg, Toast.LENGTH_LONG).show();
            return isValid;
        }

        if (goalie.mJerseyNumber == lw.mJerseyNumber || goalie.mJerseyNumber == center.mJerseyNumber ||
                goalie.mJerseyNumber == rw.mJerseyNumber || goalie.mJerseyNumber == ld.mJerseyNumber ||
                goalie.mJerseyNumber == rd.mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as a goalie. He can't play multiple positions at the same time", goalie.mJerseyNumber);
            Toast.makeText(this.getBaseContext(),errorMsg, Toast.LENGTH_LONG).show();
            return isValid;
        }

        isValid = true;
        return isValid;
    }
}
