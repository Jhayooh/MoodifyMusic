package com.example.moodifymusic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragment extends Fragment {
    private ImageView addPlaylist;
    private List<Playlist> pListData;
    private PlaylistAdapter pListAdapter;
    private RecyclerView pListRecyclerView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaylistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragment newInstance(String param1, String param2) {
        PlaylistFragment fragment = new PlaylistFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        addPlaylist = view.findViewById(R.id.addplaylist);
        pListRecyclerView = view.findViewById(R.id.recyclerview_playlist);
        pListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pListData = new ArrayList<>();
        pListAdapter = new PlaylistAdapter(this.getContext(), (ArrayList<Playlist>) pListData);
        pListRecyclerView.setAdapter(pListAdapter);
        if (currentUser != null){
            initializeData();
        }
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser != null){
                    DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Playlist").child(currentUser.getUid());
                    DatabaseReference newReff = dbreff.push();
                    String newReff_id = newReff.getKey();
                    DatabaseReference new_db = dbreff.child(newReff_id);
                    Map<String, Object> initData = new HashMap<>();
                    initData.put("musics", null);
                    initData.put("pList_name", newReff_id);
                    initData.put("user_name", "me");
                    new_db.setValue(initData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "You must login first to create a playlist", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void initializeData() {
        DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Playlist").child(currentUser.getUid());
        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pListData.clear();
                for (DataSnapshot playlistSnapshot : snapshot.getChildren()) {
                    Playlist playlist = playlistSnapshot.getValue(Playlist.class);
                    pListData.add(playlist);
                }
                pListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}