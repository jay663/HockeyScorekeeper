package com.scoreit.hockeyscorekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scoreit.hockeyscorekeeper.model.Team;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.TeamViewHolder> {
    private final LayoutInflater mInflater;
    private Context mContext;
    private List<Team> mTeams; // Cached copy of teams
    private int mSelectedTeam;
    private BottomNavigationView mMenu;
    private FloatingActionButton mAddButton;


    public TeamListAdapter(Context context, BottomNavigationView bottomMenu, FloatingActionButton addButton) {
        mInflater = LayoutInflater.from(context);
        mAddButton = addButton;
        mMenu = bottomMenu;
        mContext = context;
        mSelectedTeam = -1;
    }

    public Team getSelectedTeam() {
        if (mSelectedTeam < 0)
        {
            return null;
        }

        return mTeams.get(mSelectedTeam);
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        public final TextView teamItemView;
        public final CheckBox teamItemCheckBox;
        public final CardView teamItemCL;

        public View layout;

        private TeamViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            teamItemView = itemView.findViewById(R.id.team_item_name);
            teamItemCL = itemView.findViewById(R.id.team_item_cl);
            teamItemCheckBox = itemView.findViewById(R.id.team_item_checkBox);
        }
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.team_view_item, parent, false);
        return new TeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, int position) {
        if (mTeams != null) {
            Team current = mTeams.get(position);
            String name = String.format("%s %s", current.mLocation, current.mTeamName);
            holder.teamItemView.setText(name);
        } else {
            // Covers the case of data not being ready yet.
            holder.teamItemView.setText("No Teams Have Been Added");
        }

        holder.teamItemCheckBox.setChecked((mSelectedTeam == position));
        holder.teamItemCL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean isSelected = holder.teamItemCheckBox.isChecked();
                holder.teamItemCheckBox.setChecked(!isSelected);
                if(isSelected){
                    mSelectedTeam = -1;
                    holder.teamItemCheckBox.setVisibility(View.GONE);
                    mMenu.setVisibility(View.GONE);
                    mAddButton.show();
                }else{
                    mSelectedTeam = position;
                    holder.teamItemCheckBox.setVisibility(View.VISIBLE);
                    mMenu.setVisibility(View.VISIBLE);
                    mAddButton.hide();
                }

            }
        });
    }

    void setTeams(List<Team> teams){
        mTeams = teams;
        notifyDataSetChanged();
    }

    public Team removeItem(int position)
    {
        Team team = mTeams.get(position);
        mTeams.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mTeams.size());
        return team;

        //                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle("Choose option");
//                builder.setMessage("Update roster or edit team name?");
//                builder.setPositiveButton("Update Roster", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //go to update activity
//                        //goToUpdateActivity(person.getId());
//                        Log.d(TAG, "Selected Update Roster");
//
//                    }
//                });
//                builder.setNeutralButton("Edit Team Name", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d(TAG, "Selected Edit Team Name ");
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.create().show();ß
    }

    public void restoreItem(Team team, int position)
    {
        mTeams.add(position, team);
        notifyItemInserted(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mTeams has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTeams != null)
            return mTeams.size();
        else return 0;
    }

}
