package com.example.moodifymusic;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodifymusic.MainFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayingFrame extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    TextView titleTv, currentTimeTv, totalTimeTv, authorTv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, prevBtn, musicIcon, hambergerMnu;
    ArrayList<Music> musicItemData;
    Music currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing_frame);

        titleTv = findViewById(R.id.mTitle);
        authorTv = findViewById(R.id.mAuthor);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause);
        nextBtn = findViewById(R.id.skipforward);
        prevBtn = findViewById(R.id.skipback);
        musicIcon = findViewById(R.id.albumart);
        hambergerMnu = findViewById(R.id.hamburgerMenu);

        hambergerMnu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MusicPlayingFrame.this, QueueFrame.class);
                startActivity(intent);
            }
        });


        titleTv.setSelected(true);

        musicItemData = (ArrayList<Music>) getIntent().getSerializableExtra("LIST:");

        mediaPlayer = MainFrame.myMediaPlayer.getInstance();
        Intent intent = getIntent();
        titleTv.setText(intent.getStringExtra("title"));
        authorTv.setText(intent.getStringExtra("author"));
        totalTimeTv.setText(intent.getStringExtra("duration"));


        pausePlay.setOnClickListener(v-> pauseMusic());
        nextBtn.setOnClickListener(v-> playNextMusic());
        prevBtn.setOnClickListener(v-> playPreviousMusic());

        playMusic();

        MusicPlayingFrame.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));
                    if (mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.baseline_pause_24);
                    }
                    else{
                        pausePlay.setImageResource(R.drawable.ic_playtugtog_foreground);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void playMusic(){
        if (getIntent().hasExtra("audio")){
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(getIntent().getStringExtra("audio"));
                mediaPlayer.prepare();
                mediaPlayer.start();
                seekBar.setProgress(0);
                seekBar.setMax(mediaPlayer.getDuration());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
    public void playNextMusic(){

    }
    public void playPreviousMusic(){

    }
    public void pauseMusic() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }


    public void back_btn(View view) {
        finish();
    }

    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }


}