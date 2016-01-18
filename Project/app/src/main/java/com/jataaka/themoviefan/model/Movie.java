package com.jataaka.themoviefan.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String thumbnailUrl;
    private String overview;
    private int year;
    private double voteAvarage;
    private int voteCount;
    private ArrayList<Integer> genre;

    public Movie(int id, String title, String thumbnailUrl, String overview, String reteaseDate, double voteAvarage, int voteCount, ArrayList<Integer> genre) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.overview = overview;
        if (reteaseDate.length()>=4){
            this.year = Integer.parseInt(reteaseDate.substring(0, 4));
        }else{
            this.year = 0;
        }
        this.voteAvarage = voteAvarage;
        this.voteCount = voteCount;
        this.id = id;
        this.genre = genre;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public int getYear() {
        return this.year;
    }

    public double getVoteAvarage() {
        return this.voteAvarage;
    }

    public double getVoteCount() {
        return this.voteCount;
    }

    public ArrayList<Integer> getGenreIds() {
        return this.genre;
    }
}