package com.example.moodifymusic;

public class Playlist {
    private String plist_name;
    private String user_name;

    public Playlist(){

    }
    public Playlist(String plist_name, String user_name){
        this.plist_name = plist_name;
        this.user_name = user_name;
    }

    public String getPlist_name() {
        return plist_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPlist_name(String plist_name) {
        this.plist_name = plist_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
