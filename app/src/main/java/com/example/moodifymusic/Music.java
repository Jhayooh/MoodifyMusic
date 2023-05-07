package com.example.moodifymusic;

public class Music {
    private String title;
    private String artist;
    private String image;
    private String audio;

    // empty constructor
    public Music(){

    }

    public Music(String title, String artist, String image, String audio){
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.audio = audio;
    }

    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getImage(){
        return image;
    }
    public String getAudio(){
        return audio;
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
}
