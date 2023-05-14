package com.example.moodifymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {
    private ArrayList<Mood> moodItem;
    private Context context;

    public MoodAdapter(Context context, ArrayList<Mood> moodItem){
        this.context = context;
        this.moodItem = moodItem;
    }
    @NonNull
    @Override
    public MoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.mood_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoodAdapter.ViewHolder holder, int position) {
        Mood currentMood = moodItem.get(position);
        holder.bindTo(currentMood);
    }

    @Override
    public int getItemCount() {
        return moodItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mood_label;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mood_label = itemView.findViewById(R.id.mood_label);
            itemView.setOnClickListener(this);
        }

        public void bindTo(Mood currentMood) {
            Mood currentmood = moodItem.get(getAdapterPosition());
            mood_label.setText(currentMood.getMood());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
