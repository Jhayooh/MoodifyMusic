package com.example.moodifymusic;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;

public class MainFrame extends AppCompatActivity {

    private Intent myIntentMusicPlaying;
    DatabaseReference dbreff;

    String link;
    BottomNavigationView bottomNavigationView;

    HomeFragment home = new HomeFragment();
    SearchFragment search = new SearchFragment();
    PlaylistFragment playlist = new PlaylistFragment();

    public static class getMyIntentMusicPlaying {
        private static Intent intent = new Intent();

        private getMyIntentMusicPlaying(){

        }
        public static Intent getMyIntent(Context context){
            intent = new Intent(context, MusicPlayingFrame.class);
            return intent;
        }
    }

    public static class myMediaPlayer{
        private static MediaPlayer mediaPlayer;
        private myMediaPlayer(){
            
        }
        public static synchronized MediaPlayer getInstance() {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            return mediaPlayer;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);

        bottomNavigationView = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, home).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, home).commit();
                        item.getIcon().setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_IN);
                        return true;
                    case R.id.nav_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, search).commit();
                        item.getIcon().setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_IN);
                        return true;
                    case R.id.nav_playlist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, playlist).commit();
                        item.getIcon().setColorFilter(getResources().getColor(R.color.accent), PorterDuff.Mode.SRC_IN);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.music_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().startActivity(getMyIntentMusicPlaying.getMyIntent(getApplicationContext()));
            }
        });
    }

}