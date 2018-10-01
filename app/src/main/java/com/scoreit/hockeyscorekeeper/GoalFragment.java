package com.scoreit.hockeyscorekeeper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.scoreit.hockeyscorekeeper.adapters.PlayerArrayAdapter;
import com.scoreit.hockeyscorekeeper.model.Player;
import com.scoreit.hockeyscorekeeper.viewmodel.GoalViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class GoalFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    public static final String ADD_GOAL_TEAM_ID = "ADD_GOAL_TEAM_ID";
    public static final String ADD_GOAL_GAME_ID = "ADD_GOAL_GAME_ID";
    public static final String ADD_GOAL_HOME_OR_AWAY_ID = "ADD_GOAL_HOME_OR_AWAY_ID";
    public static final String ADD_GOAL_TIME = "ADD_GOAL_TIME";
    public static final String ADD_GOAL_PERIOD = "ADD_GOAL_PERIOD";

    private GoalViewModel mViewModel;
    private long mGameId;
    private int mTeamId;
    private String mHomeOrAway;
    private String mGoalTime;
    private int mCurrentPeriod;
    private Spinner mScorerSpinner;
    private Spinner mAssistSpinner;
    private Spinner mSecondAssistSpinner;


    public static GoalFragment newInstance() {
        return new GoalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_fragment, container, false);

        mGameId = getArguments().getLong(ADD_GOAL_GAME_ID);
        mTeamId = getArguments().getInt(ADD_GOAL_TEAM_ID);
        mHomeOrAway = getArguments().getString(ADD_GOAL_HOME_OR_AWAY_ID);
        mGoalTime = getArguments().getString(ADD_GOAL_TIME);
        mCurrentPeriod = getArguments().getInt(ADD_GOAL_PERIOD);

        mViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        mViewModel.initialize(mGameId, mTeamId, mHomeOrAway, mGoalTime, mCurrentPeriod);

        mScorerSpinner = (Spinner) view.findViewById(R.id.scoring_player_spinner);
        mAssistSpinner = (Spinner) view.findViewById(R.id.assist_player_spinner);
        mSecondAssistSpinner = (Spinner) view.findViewById(R.id.secondary_assist_spinner);

        mViewModel.getActivePlayers().observe(this, playerList -> {
            mViewModel.setScorerAdapter(new PlayerArrayAdapter(getContext(),
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setAssistAdapter(new PlayerArrayAdapter(getContext(),
                    R.layout.player_spinner_layout, playerList));
            mViewModel.setSecondaryAssistAdapter(new PlayerArrayAdapter(getContext(),
                    R.layout.player_spinner_layout, playerList));

            mScorerSpinner.setAdapter(mViewModel.getScorerAdapter());
            mAssistSpinner.setAdapter(mViewModel.getAssistAdapter());
            mSecondAssistSpinner.setAdapter(mViewModel.getSecondaryAssistAdapter());
        });


        mScorerSpinner.setOnItemSelectedListener(this);
        mAssistSpinner.setOnItemSelectedListener(this);
        mSecondAssistSpinner.setOnItemSelectedListener(this);

        Button saveButton = (Button)view.findViewById(R.id.save_goal_button);
        Button cancelButton = (Button)view.findViewById(R.id.cancel_goal_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoalSaveClicked(view);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoalCancelClicked(view);
            }
        });


        return view;
    }

    public boolean isValid()
    {
        boolean isValid = true;
        String errorMsg = "";

        if (mViewModel.getScorer().mJerseyNumber == mViewModel.getAssist().mJerseyNumber ||
                mViewModel.getScorer().mJerseyNumber == mViewModel.getSecondaryAssist().mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as the scoring player. He can't be the assisting player at the same time",
                    mViewModel.getScorer().mPlayerName);
            Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG).show();
            return false;
        }

        if (mViewModel.getAssist().mJerseyNumber == mViewModel.getSecondaryAssist().mJerseyNumber)
        {
            errorMsg = String.format("Error! %s is already selected as the assist player.",
                    mViewModel.getScorer().mPlayerName);
            Toast.makeText(getActivity(),errorMsg, Toast.LENGTH_LONG).show();
            return false;
        }

        return isValid;
    }

    public void onGoalSaveClicked(View view){
        if(isValid()) {
            mViewModel.savedGoal(this);
        }
    }

    public void onGoalCancelClicked(View view){
        ((PlayGameActivity)getActivity()).goalFragmentCanceled();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Player player = (Player) adapterView.getSelectedItem();
        mViewModel.onPlayerSelected(adapterView, player);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}
