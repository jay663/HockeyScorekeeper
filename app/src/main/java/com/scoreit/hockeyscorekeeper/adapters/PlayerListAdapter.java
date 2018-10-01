package com.scoreit.hockeyscorekeeper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scoreit.hockeyscorekeeper.R;
import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {
    private final LayoutInflater mInflater;
    private List<Player> mPlayers; // Cached copy of teams
    private int mSelectedPlayer;
    private int mTeamId;
    private BottomNavigationView mMenu;
    private FloatingActionButton mAddButton;

    public PlayerListAdapter(Context context, BottomNavigationView bottomMenu, FloatingActionButton addButton, int teamId) {
        mInflater = LayoutInflater.from(context);
        mMenu = bottomMenu;
        mAddButton = addButton;
        mTeamId = teamId;
        mSelectedPlayer = -1;
    }

    public Player getSelectedPlayer() {
        if (mSelectedPlayer < 0)
        {
            return null;
        }

        return mPlayers.get(mSelectedPlayer);
    }

    public void clearSelectedPlayer() {
        notifyItemChanged(mSelectedPlayer);
        mSelectedPlayer = -1;
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView playerNameView;
        private final TextView playerJerseyView;
        private final TextView playerPositionView;
        private final CheckBox playerItemCheckBox;
        private final CardView playerItemCL;

        private PlayerViewHolder(View itemView) {
            super(itemView);
            playerNameView = itemView.findViewById(R.id.player_name_item);
            playerJerseyView = itemView.findViewById(R.id.player_jersey_item);
            playerPositionView = itemView.findViewById(R.id.player_position_item);
            playerItemCL = itemView.findViewById(R.id.roster_player_cl);
            playerItemCheckBox = itemView.findViewById(R.id.roster_player_checkbox);
        }
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.roster_player_view_item, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        if (mPlayers != null) {
            Player current = mPlayers.get(position);
            holder.playerNameView.setText(current.mPlayerName);
            String jersey = String.format("%2s", current.mJerseyNumber);
            holder.playerJerseyView.setText(jersey);
            holder.playerPositionView.setText(current.mPosition);
        } else {
            // Covers the case of data not being ready yet.
            holder.playerNameView.setText("No Players Have Been Added");
            holder.playerJerseyView.setText(" ");
            holder.playerPositionView.setText(" ");
        }

        holder.playerItemCheckBox.setChecked((mSelectedPlayer == position));
        if (mSelectedPlayer == position)
        {
            holder.playerItemCheckBox.setVisibility(View.VISIBLE);
        }else{
            holder.playerItemCheckBox.setVisibility(View.GONE);
        }

        holder.playerItemCL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean isSelected = holder.playerItemCheckBox.isChecked();
                holder.playerItemCheckBox.setChecked(!isSelected);
                if(isSelected){
                    mSelectedPlayer = -1;
                    holder.playerItemCheckBox.setVisibility(View.GONE);
                    mMenu.setVisibility(View.GONE);
                    mAddButton.show();
                }else{
                    mSelectedPlayer = position;
                    holder.playerItemCheckBox.setVisibility(View.VISIBLE);
                    mMenu.setVisibility(View.VISIBLE);
                    mAddButton.hide();
                }
                notifyDataSetChanged();
            }
        });


    }

    public void setPlayers(List<Player> players){
        mPlayers = players;
        notifyDataSetChanged();
    }

    public Player removeItem(int position)
    {
        Player player = mPlayers.get(position);
        mPlayers.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPlayers.size());
        return player;
    }

    // getItemCount() is called many times, and when it is first called,
    // mTeams has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPlayers != null)
            return mPlayers.size();
        else return 0;
    }
}
