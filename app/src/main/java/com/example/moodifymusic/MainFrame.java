package com.example.moodifymusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainFrame extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment home = new HomeFragment();
    SearchFragment search = new SearchFragment();
    PlaylistFragment playlist = new PlaylistFragment();

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
    }
}