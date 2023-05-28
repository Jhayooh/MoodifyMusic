package com.example.moodifymusic;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private Button addBtn;
    private String spinnerLabel;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Playlist");
    private String userId = mAuth.getCurrentUser().getUid();
    private ArrayList<String> playlists = new ArrayList<>();
    public static MyDialog newInstance(String extraString) {
        MyDialog fragment = new MyDialog();
        Bundle args = new Bundle();
        args.putString("musicId", extraString);
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        String musicId = getArguments().getString("musicId");
        addBtn = dialogView.findViewById(R.id.btn_add);
        spinner = dialogView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, playlists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        if (mAuth.getCurrentUser() != null) {
            dbreff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot: snapshot.child(userId).getChildren()
                         ) {
                        Playlist playlist = dataSnapshot.getValue(Playlist.class);
                        String playlist_name = playlist.getPlist_name();
                        playlists.add(playlist_name);

                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        // ...

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference musicRef = FirebaseDatabase.getInstance()
                        .getReference("Playlist")
                        .child(currentUser.getUid())
                        .child(spinnerLabel)
                        .child("musics");

                musicRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // The "musics" key exists
                            ArrayList<Integer> existingMusics = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Integer music = snapshot.getValue(Integer.class);
                                if (music != null) {
                                    existingMusics.add(music);
                                }
                            }
                            // Add the new item to the existing array
                            existingMusics.add(Integer.valueOf(musicId));

                            // Set the updated array as the new value for the "musics" key
                            musicRef.setValue(existingMusics)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Item added successfully
                                            Toast.makeText(getContext(), "Item added to playlist", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error occurred while adding the item
                                            Toast.makeText(getContext(), "Failed to add item", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "Error adding item to Firebase database", e);
                                        }
                                    });
                        } else {
                            // The "musics" key doesn't exist, create a new array with the item
                            ArrayList<Integer> newMusics = new ArrayList<>();
                            newMusics.add(Integer.valueOf(musicId));

                            // Set the new array as the value for the "musics" key
                            musicRef.setValue(newMusics)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Item added successfully
                                            Toast.makeText(getContext(), "Item added to playlist", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, musicId);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error occurred while adding the item
                                            Toast.makeText(getContext(), "Failed to add item", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "Error adding item to Firebase database", e);
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled event if needed
                    }
                });
            }
        });

// ...

        builder.setView(dialogView);
        return builder.create();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerLabel = adapterView.getItemAtPosition(i).toString();
        spinner.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
