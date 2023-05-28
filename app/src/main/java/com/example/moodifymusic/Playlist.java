package com.example.moodifymusic;

import java.util.ArrayList;

public class Playlist {
    private String pList_name;
    private String user_name;
    private String user_id;
    private ArrayList<Integer> musics;

    public Playlist(){

    }
    public Playlist(String pList_name, String user_name, String user_id, ArrayList<Integer> musics){
        this.pList_name = pList_name;
        this.user_name = user_name;
        this.user_id = user_id;
        this.musics = musics;
    }

    public String getPlist_name() {
        return pList_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPlist_name(String pList_name) {
        this.pList_name = pList_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<Integer> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Integer> musics) {
        this.musics = musics;
    }
}
