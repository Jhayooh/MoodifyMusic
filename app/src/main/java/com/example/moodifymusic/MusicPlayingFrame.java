package com.example.moodifymusic;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moodifymusic.MainFrame;

import java.io.IOException;

public class MusicPlayingFrame extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing_frame);
        mediaPlayer = MainFrame.myMediaPlayer.getInstance();
        Intent intent = getIntent();
        if (intent.hasExtra("audio")){
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(intent.getStringExtra("audio"));
                playAudio();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }



    }
    public void back_btn(View view) {
        finish();
    }

    private void playAudio() {
            // Set the audio attributes for playback
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();
            mediaPlayer.setAudioAttributes(audioAttributes);

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
    }
}