package com.scoreit.hockeyscorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.scoreit.hockeyscorekeeper.model.Coach;
import com.scoreit.hockeyscorekeeper.viewmodel.CoachViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

public class AddCoachActivity extends AppCompatActivity {
    public static final String ADD_COACH_TEAM_ID = "ADD_COACH_TEAM_ID";

    private CoachViewModel mCoachViewModel;
    private EditText mCoachNameTextBox;
    private Spinner mCoachTitleSpinner;
    private int mTeamId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coach);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mTeamId = intent.getIntExtra(ADD_COACH_TEAM_ID, -1);
        mCoachViewModel = ViewModelProviders.of(this).get(CoachViewModel.class);

        mCoachNameTextBox = findViewById(R.id.add_coach_name_et);
        mCoachTitleSpinner = (Spinner) findViewById(R.id.add_coach_title_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.coach_title_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCoachTitleSpinner.setAdapter(adapter);
    }

    public void onAddCoachBtnClick(View view) {
        Intent replyIntent = new Intent();

        if (validInput()) {
            String coachName = mCoachNameTextBox.getText().toString();
            String title = mCoachTitleSpinner.getSelectedItem().toString();

            Coach coach = new Coach(coachName, mTeamId, title);
            mCoachViewModel.insert(coach);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    private boolean validInput() {
        if(TextUtils.isEmpty(mCoachNameTextBox.getText())) {
            Toast.makeText(getApplicationContext(), "Please enter a coach name", Toast.LENGTH_SHORT).show();
            return false;
        }

        String title = mCoachTitleSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(title)){
            Toast.makeText(getApplicationContext(), "Please select a coach title", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void onCancelBtnClick(View view) {
        finish();
    }
}
