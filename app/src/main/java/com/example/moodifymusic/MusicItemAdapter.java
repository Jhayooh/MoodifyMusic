package com.example.moodifymusic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodifymusic.MainFrame;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.ViewHolder>{
private final int limit = 10;
    private ArrayList<Music> musicItemData;
    private Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        return Math.min(musicItemData.size(), limit);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        private TextView mTitleText;
        private TextView mAuthorText;
        private ImageView mImage, more;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            more = itemView.findViewById(R.id.more);
            mTitleText = itemView.findViewById(R.id.music_title);
            mAuthorText = itemView.findViewById(R.id.music_artist);
            mImage = itemView.findViewById(R.id.music_image);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context, more);
                    popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(ViewHolder.this); // Set the listener to the ViewHolder instance
                    popupMenu.show();
                }
            });

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

        @Override
        public boolean onLongClick(View view) {

            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                MyDialog dialogFragment = new MyDialog();
                dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog_fragment_tag");
                return true;
            } else {
                Toast.makeText(context, "Login to customize a playlist", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
    }
}