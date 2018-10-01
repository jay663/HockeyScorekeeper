package com.scoreit.hockeyscorekeeper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.scoreit.hockeyscorekeeper.R;
import com.scoreit.hockeyscorekeeper.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerArrayAdapter extends ArrayAdapter<Player> implements Filterable {
    //private final List<Player> mPlayers;
    private List<Player> mFilteredPlayers;
    private List<Player> mAllPlayers;
    private final int mResource;
    private ExcludePlayersFilter mFilter;

    public PlayerArrayAdapter(Context context, int resource, List<Player> players) {
        super(context, resource, players);
        mFilteredPlayers = players;
        mAllPlayers = players;
        mResource = resource;
        mFilter = new ExcludePlayersFilter();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(mResource, parent, false);

        TextView jerseyTv = (TextView) view.findViewById(R.id.player_spinner_jersey);
        TextView firstNameTv = (TextView) view.findViewById(R.id.player_spinner_firstName);
        TextView lastNameTv = (TextView) view.findViewById(R.id.player_spinner_lastName);
        TextView positionTv = (TextView) view.findViewById(R.id.player_spinner_position);
        Player player = getItem(position);
        String[] names = player.mPlayerName.split(" ");
        String jerseyValue = String.format("%2s", player.mJerseyNumber);
        String firstName = names[0];
        String lastname = names[1];

        jerseyTv.setText(jerseyValue);
        firstNameTv.setText(firstName);
        lastNameTv.setText(lastname);
        positionTv.setText(player.mPosition);

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ExcludePlayersFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0){
                results.values = mAllPlayers;
                results.count = mAllPlayers.size();
                return results;
            }

            String filterString = constraint.toString();
            String playerIds[] = filterString.split(" ");

            int count = mAllPlayers.size();
            final ArrayList<Player> nlist = new ArrayList<Player>(count);

            for (int i = 0; i < mAllPlayers.size(); i++) {
                String jersey = String.valueOf(mAllPlayers.get(i).mJerseyNumber);
                if (!Arrays.asList(playerIds).contains(jersey)){
                    nlist.add(mAllPlayers.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                mFilteredPlayers = (List<Player>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}
