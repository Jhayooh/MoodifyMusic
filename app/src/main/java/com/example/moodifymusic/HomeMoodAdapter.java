package com.example.moodifymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeMoodAdapter extends RecyclerView.Adapter<HomeMoodAdapter.ViewHolder> {
    private ArrayList<Mood> hMoodItem;
    private Context context;
    public HomeMoodAdapter(Context context, ArrayList<Mood> hMoodItem){
        this.context = context;
        this.hMoodItem = hMoodItem;
    }
    @NonNull
    @Override
    public HomeMoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.categorize, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMoodAdapter.ViewHolder holder, int position) {
        Mood currentMood = hMoodItem.get(position);
        holder.bindTo(currentMood);

    }

    @Override
    public int getItemCount() {
        return Math.min(hMoodItem.size(), 10);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView moodLabel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moodLabel = itemView.findViewById(R.id.mood_name);
        }

        public void bindTo(Mood currentMood) {
            Mood currentmood = hMoodItem.get(getAdapterPosition());
            moodLabel.setText(currentMood.getMood());
        }
    }
}
