package com.example.moodifymusic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference dbreff = FirebaseDatabase.getInstance().getReference("Playlist");
    private String userId = mAuth.getCurrentUser().getUid();
    private ArrayList<String> playlists = new ArrayList<>();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
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
                            
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        spinner = dialogView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, playlists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        builder.setView(dialogView);
        return builder.create();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
