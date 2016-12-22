package com.example.android.popularmovies.data;

public class Movie {

    private int id;
    private String title;
    private String release_date;
    private String overview;
    private String[] genre_ids;
    private String post_path;
    private String backdrop_path;
    private int vote_count;
    private double vote_average;
    private double popularity;

    public Movie() {

    }

    public String getTitle() {

        return title;
    }
}
