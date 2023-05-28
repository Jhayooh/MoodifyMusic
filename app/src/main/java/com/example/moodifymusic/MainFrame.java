package com.example.moodifymusic;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;

public class MainFrame extends AppCompatActivity {

    private Button btn_register;
    private FirebaseAuth mAuth;
    private TextView login;
    private FirebaseUser mUser;
    private Intent myIntentMusicPlaying;
    DatabaseReference dbreff;

    String link;
    BottomNavigationView bottomNavigationView;

    HomeFragment home = new HomeFragment();
    SearchFragment search = new SearchFragment();
    PlaylistFragment playlist = new PlaylistFragment();
    ConstraintLayout layout;
    public boolean isLoggedIn;

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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, home).commit();
        layout = findViewById(R.id.drawer_layout);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, home).commit();
                        return true;
                    case R.id.nav_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, search).commit();
                        return true;
                    case R.id.nav_playlist:
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, playlist).commit();
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

        CreatepopupWindow();
    }

    private void CreatepopupWindow() {
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpView=inflater.inflate(R.layout.moodpopup,null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER,0, 0);
            }
        });
        Button energeticbtn, relaxingbtn, happybtn, sadbtn, motivationalbtn, chillbtn, romanticbtn;
        energeticbtn = popUpView.findViewById(R.id.energetic);
        relaxingbtn = popUpView.findViewById(R.id.relaxing);
        happybtn = popUpView.findViewById(R.id.happy);
        sadbtn = popUpView.findViewById(R.id.sad);
        motivationalbtn = popUpView.findViewById(R.id.motivational);
        chillbtn = popUpView.findViewById(R.id.chill);
        romanticbtn = popUpView.findViewById(R.id.romantic);

        energeticbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
        relaxingbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
        happybtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
        sadbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
        motivationalbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
        chillbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });
        romanticbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
            }
        });

    }

}