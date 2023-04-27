package com.example.moodifymusic;

public class Music {
    private String mTitle;
    private String mAuthor;

    Music(String mTitle, String mAuthor){
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
    }

    String getTitle(){return mTitle;}
    String getAuthor(){return mAuthor;}
}
