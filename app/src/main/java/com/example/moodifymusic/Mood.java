package com.example.moodifymusic;

import java.util.ArrayList;

public class Mood {
    private String mood;
    private ArrayList<Integer> musics;
    int id;
    public Mood() {

    }
    public Mood(String mood, ArrayList<Integer> musics, int id){
        this.mood = mood;
        this.musics = musics;
        this.id = id;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public ArrayList<Integer> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Integer> musics) {
        this.musics = musics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
