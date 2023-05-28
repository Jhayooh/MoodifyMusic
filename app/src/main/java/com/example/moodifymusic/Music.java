package com.example.moodifymusic;

import java.io.Serializable;

public class Music implements Serializable {
    private String title;
    private String artist;
    private String image;
    private String audio;
    private String duration;
    private int id;

    // empty constructor
    public Music(){

    }

    public Music(String title, String artist, String image, String audio, String duration, int id){
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.audio = audio;
        this.duration = duration;
        this.id = id;
    }

    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getImage(){
        return image;
    }
    public String getAudio(){
        return audio;
    }
    public String getDuration() {
        return duration;
    }
    public void setTitle(){
        this.title = title;
    }
    public void setArtist(){
        this.artist = artist;
    }
    public void setImage(){
        this.image = image;
    }
    public void setAudio(){
        this.audio = audio;
    }
    public void setDuration() {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
