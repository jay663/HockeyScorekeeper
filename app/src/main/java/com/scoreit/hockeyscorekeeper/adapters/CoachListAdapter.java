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
import com.scoreit.hockeyscorekeeper.model.Coach;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CoachListAdapter extends RecyclerView.Adapter<CoachListAdapter.CoachViewHolder> {
    private final LayoutInflater mInflater;
    private Context mContext;
    private List<Coach> mCoaches;
    private int mSelectedCoach;
    private BottomNavigationView mMenu;
    private int mTeamId;
    private FloatingActionButton mAddButton;


    public CoachListAdapter(Context context, BottomNavigationView bottomMenu, FloatingActionButton addButton, int teamId) {
        mInflater = LayoutInflater.from(context);
        mAddButton = addButton;
        mMenu = bottomMenu;
        mContext = context;
        mTeamId = teamId;
        mSelectedCoach = -1;
    }

    public Coach getSelectedCoach() {
        if (mSelectedCoach < 0)
        {
            return null;
        }

        return mCoaches.get(mSelectedCoach);
    }

    public void clearSelectedCoach(){
        notifyItemChanged(mSelectedCoach);
        mSelectedCoach = -1;
    }

    public class CoachViewHolder extends RecyclerView.ViewHolder {
        public final TextView coachNameItemTextView;
        public final TextView coachTitleItemTextView;
        public final CheckBox coachItemCheckBox;
        public final CardView coachItemCL;

        private CoachViewHolder(View itemView) {
            super(itemView);
            coachItemCL = itemView.findViewById(R.id.coaches_cl);
            coachNameItemTextView = itemView.findViewById(R.id.coaches_name_item);
            coachTitleItemTextView = itemView.findViewById(R.id.coaches_title_item);
            coachItemCheckBox = itemView.findViewById(R.id.coaches_checkbox);
        }
    }

    @Override
    public CoachListAdapter.CoachViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.coaches_view_item, parent, false);
        return new CoachListAdapter.CoachViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoachListAdapter.CoachViewHolder holder, int position) {
        if (mCoaches != null) {
            Coach current = mCoaches.get(position);
            holder.coachNameItemTextView.setText(current.mName);
            holder.coachTitleItemTextView.setText(current.mTypeOfCoach);
        } else {
            // Covers the case of data not being ready yet.
            holder.coachNameItemTextView.setText("No Coaches Have Been Added");
        }

        holder.coachItemCheckBox.setChecked((mSelectedCoach == position));
        if (mSelectedCoach == position)
        {
            holder.coachItemCheckBox.setVisibility(View.VISIBLE);
        }else{
            holder.coachItemCheckBox.setVisibility(View.GONE);
        }


        holder.coachItemCL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean isSelected = holder.coachItemCheckBox.isChecked();
                holder.coachItemCheckBox.setChecked(!isSelected);
                if(isSelected){
                    mSelectedCoach = -1;
                    holder.coachItemCheckBox.setVisibility(View.GONE);
                    mMenu.setVisibility(View.GONE);
                    mAddButton.show();
                }else{
                    mSelectedCoach = position;
                    holder.coachItemCheckBox.setVisibility(View.VISIBLE);
                    mMenu.setVisibility(View.VISIBLE);
                    mAddButton.hide();
                }
                notifyDataSetChanged();
            }
        });

    }

    public void setCoaches(List<Coach> coaches){
        mCoaches = coaches;
        notifyDataSetChanged();
    }

    public Coach removeItem(int position)
    {
        Coach coach = mCoaches.get(position);
        mCoaches.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mCoaches.size());
        return coach;

    }

    public void restoreItem(Coach coach, int position)
    {
        mCoaches.add(position, coach);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        if (mCoaches != null)
            return mCoaches.size();
        else return 0;
    }

}
