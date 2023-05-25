package com.example.moodifymusic;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ImageView imageView;
    ViewPager viewPager;
    ArrayList<Integer> images = new ArrayList<>();
    ViewPagerAdapter vAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private List<Mood> hMoodList;
    private HomeMoodAdapter hMoodAdapter;
    private List<Music> mMusicData;
    private MusicItemAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void initializeData() {
        DatabaseReference dbreff_hm = FirebaseDatabase.getInstance().getReference("Mood");
        DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Music");

        dbreff_hm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hMoodList.clear();
                for (DataSnapshot moodSnapshot:snapshot.getChildren()){
                    Mood mood = moodSnapshot.getValue(Mood.class);
                    hMoodList.add(mood);
                }
                hMoodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMusicData.clear();
                for (DataSnapshot musicSnapshot : snapshot.getChildren()) {
                    Music music = musicSnapshot.getValue(Music.class);
                    mMusicData.add(music);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        images.add(R.drawable.image1);
        images.add(R.drawable.image2);
        images.add(R.drawable.image3);
        vAdapter = new ViewPagerAdapter(this.getContext(), images);
        viewPager.setAdapter(vAdapter);
        imageView = view.findViewById(R.id.account);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), imageView);
                popupMenu.getMenuInflater().inflate(R.menu.account_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.login:
                                Intent intent1 = new Intent(getContext(), LoginFrame.class);
                                HomeFragment.this.startActivity(intent1);
                                return true;
                            case R.id.register:
                                Intent intent2 = new Intent(getContext(), RegisterFrame.class);
                                HomeFragment.this.startActivity(intent2);
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView2 = view.findViewById(R.id.recyclerview2);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ArrayList that will contain the data.
        hMoodList = new ArrayList<>();
        mMusicData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        hMoodAdapter = new HomeMoodAdapter(this.getContext(), (ArrayList<Mood>) hMoodList);
        mRecyclerView2.setAdapter(hMoodAdapter);
        mAdapter = new MusicItemAdapter(this.getContext(), (ArrayList<Music>) mMusicData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
        // Inflate the layout for this fragment
        return view;
    }
}