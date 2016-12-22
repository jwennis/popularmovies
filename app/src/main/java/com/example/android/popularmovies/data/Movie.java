package com.example.android.popularmovies.data;

import java.util.List;

import android.content.ContentValues;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;


public class Movie {

    private int id;
    private String title;
    private String release_date;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private int vote_count;
    private double vote_average;
    private double popularity;
    private String[] genre_ids;

    private boolean isPopular;
    private boolean isTopRated;
    private boolean isFavorite;

    // /{movie_id} endpoint
    private String imdb_id;
    private String tagline;
    private int runtime;
    private int budget;
    private int revenue;

    private List<String> trailers; // trailers.youtube[] { String source }
    private List<String> reviews; // reviews.results[] { ??? }

    public Movie() {

    }

    public String getTitle() {

        return title;
    }

    public String getGenres() {

        // TODO: implement this

        return "";
    }

    public ContentValues getValues() {

        ContentValues values = new ContentValues();

        values.put(MovieEntry.COL_TMDB_ID, id);
        values.put(MovieEntry.COL_TITLE, title);
        values.put(MovieEntry.COL_RELEASE_DATE, release_date);
        values.put(MovieEntry.COL_OVERVIEW, overview);
        values.put(MovieEntry.COL_POSTER_PATH, poster_path);
        values.put(MovieEntry.COL_BACKDROP_PATH, backdrop_path);
        values.put(MovieEntry.COL_VOTE_COUNT, vote_count);
        values.put(MovieEntry.COL_VOTE_AVERAGE, vote_average);
        values.put(MovieEntry.COL_POPULARITY, popularity);
        values.put(MovieEntry.COL_GENRES, getGenres());

        if(imdb_id != null) {

            values.put(MovieEntry.COL_IMDB_ID, imdb_id);
        }

        if(tagline != null) {

            values.put(MovieEntry.COL_TAGLINE, tagline);
        }

        if(runtime > 0) {

            values.put(MovieEntry.COL_RUNTIME, runtime);
        }

        if(budget > 0) {

            values.put(MovieEntry.COL_BUDGET, budget);
        }

        if(revenue > 0) {

            values.put(MovieEntry.COL_REVENUE, revenue);
        }

        values.put(MovieEntry.COL_IS_POPULAR, isPopular ? "1" : "0");
        values.put(MovieEntry.COL_IS_TOP_RATED, isTopRated ? "1" : "0");
        values.put(MovieEntry.COL_IS_FAVORITE, isFavorite ? "1" : "0");

        return values;
    }
}
