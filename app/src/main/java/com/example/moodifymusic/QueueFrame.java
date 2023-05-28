package com.example.moodifymusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class QueueFrame extends AppCompatActivity {

    private RecyclerView musicqueueRecycler;
    private List<Music> mMusicData;
    private MusicItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_frame);
        musicqueueRecycler = findViewById(R.id.mMusicqueueRecycler);
        musicqueueRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMusicData = new ArrayList<>();
        mAdapter = new MusicItemAdapter(this, (ArrayList<Music>) mMusicData);
        musicqueueRecycler.setAdapter(mAdapter);
        initializeData();

    }

    private void initializeData() {
        DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Mood");
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_queue_frame, container, false);
//
//        musicqueueRecycler = view.findViewById(R.id.mMusicqueueRecycler);
//        return view;
//    }
}