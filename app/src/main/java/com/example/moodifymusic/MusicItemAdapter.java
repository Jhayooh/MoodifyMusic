package com.example.moodifymusic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.moodifymusic.MainFrame;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.ViewHolder>{

    private ArrayList<Music> musicItemData;
    private Context context;

    MusicItemAdapter(Context context, ArrayList<Music> musicItemData){
        this.context = context;
        this.musicItemData = musicItemData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.music_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music currentMusic = musicItemData.get(position);
        holder.mTitleText.setText(currentMusic.getTitle());
        holder.bindTo(currentMusic);
    }

    @Override
    public int getItemCount() {
        return musicItemData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleText;
        private TextView mAuthorText;
        private ImageView mImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.music_title);
            mAuthorText = itemView.findViewById(R.id.music_artist);
            mImage = itemView.findViewById(R.id.music_image);

            itemView.setOnClickListener(this);
        }
        void bindTo(Music currentMusic){
            Music currentmusic = musicItemData.get(getAdapterPosition());
            mTitleText.setText(currentMusic.getTitle());
            mAuthorText.setText(currentMusic.getArtist());
            if (currentmusic.getImage() != null ){
                mImage.setImageURI(Uri.parse(currentmusic.getImage()));
            }
        }

        @Override
        public void onClick(View view) {
            Music currentmusic = musicItemData.get(getAdapterPosition());
            Intent intentMusicPlaying = MainFrame.getMyIntentMusicPlaying.getMyIntent(context);
            intentMusicPlaying.putExtra("audio", currentmusic.getAudio());
            intentMusicPlaying.putExtra("title", currentmusic.getTitle());
            intentMusicPlaying.putExtra("author", currentmusic.getArtist());
            intentMusicPlaying.putExtra("duration", currentmusic.getDuration());
            context.startActivity(intentMusicPlaying);
        }
    }
}