package com.example.moodifymusic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Hello World!", Toast.LENGTH_SHORT).show();
                int cntr = pListData.size();
                addPlaylist(String.valueOf(cntr), "New Playlist"+cntr, "Arjay Macalinao");

            }
        });
        pListRecyclerView = view.findViewById(R.id.recyclerview_playlist);
        pListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pListData = new ArrayList<>();
        pListAdapter = new PlaylistAdapter(this.getContext(), (ArrayList<Playlist>) pListData);
        pListRecyclerView.setAdapter(pListAdapter);

        initializeData();
        // Inflate the layout for this fragment
        return view;
    }

    private void initializeData() {
        DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Playlists");
        dbreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pListData.clear();
                for (DataSnapshot playlistSnapshot : snapshot.getChildren()){
                    Playlist playlist =  playlistSnapshot.getValue(Playlist.class);
                    pListData.add(playlist);
                }
                pListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addPlaylist(String userId, String name, String user) {
        Playlist playlist = new Playlist(name, user);
        DatabaseReference dbreff_add = FirebaseDatabase.getInstance().getReference("Playlists");
        dbreff_add.child(userId).setValue(playlist);
    }
}