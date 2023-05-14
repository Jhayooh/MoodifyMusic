package com.example.moodifymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private ArrayList<Playlist> pListData;
    private Context context;

    PlaylistAdapter(Context context, ArrayList<Playlist> pListData){
        this.context = context;
        this.pListData = pListData;
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        Playlist currentPlaylist = pListData.get(position);
        holder.bindTo(currentPlaylist);
    }

    @Override
    public int getItemCount() {
        return pListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView pList_name;
        private TextView user_name;
        public ViewHolder(@NonNull View pListView) {
            super(pListView);
            pList_name = pListView.findViewById(R.id.pList_title);
            user_name = pListView.findViewById(R.id.pList_user);
            pListView.setOnClickListener(this);
        }

        public void bindTo(Playlist currentPlaylist) {
            Playlist current_playlist = pListData.get(getAdapterPosition());
            pList_name.setText(current_playlist.getPlist_name());
            user_name.setText(current_playlist.getUser_name());

        }

        @Override
        public void onClick(View view) {

        }
    }
}
