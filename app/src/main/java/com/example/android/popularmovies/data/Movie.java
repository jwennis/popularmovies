package com.example.android.popularmovies.data;

import java.util.List;

import android.content.ContentValues;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;


public class Movie {

    public static final String PARAM_POPULAR = "popular";
    public static final String PARAM_TOP_RATED = "top_rated";
    public static final String PARAM_NOW_PLAYING = "now_playing";
    public static final String PARAM_UPCOMING = "upcoming";
    public static final String PARAM_FAVORITES = "favorites";

    private int id;
    private String title;
    private String release_date;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private int vote_count;
    private double vote_average;
    private double popularity;
    private int[] genre_ids;

    private boolean isPopular;
    private boolean isTopRated;
    private boolean isNowPlaying;
    private boolean isUpcoming;
    private boolean isFavorite;

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

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < genre_ids.length; i++) {

            String genre = null;

            switch(genre_ids[i]) {

                case 12: { genre = "Adventure"; break; }
                case 14: { genre = "Fantasy"; break; }
                case 16: { genre = "Animation"; break; }
                case 18: { genre = "Drama"; break; }
                case 27: { genre = "Horror"; break; }
                case 28: { genre = "Action"; break; }
                case 35: { genre = "Comedy"; break; }
                case 36: { genre = "History"; break; }
                case 37: { genre = "Western"; break; }
                case 53: { genre = "Thriller"; break; }
                case 80: { genre = "Crime"; break; }
                case 99: { genre = "Documentary"; break; }
                case 878: { genre = "Science Fiction"; break; }
                case 9648: { genre = "Mystery"; break; }
                case 10402: { genre = "Music"; break; }
                case 10749: { genre = "Romance"; break; }
                case 10751: { genre = "Family"; break; }
                case 10752: { genre = "War"; break; }
                case 10770: { genre = "TV Movie"; break; }
            }

            if(genre != null) {

                builder.append(i > 0 ? ", " + genre : genre);
            }
        }

        return builder.toString();
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

        if(isPopular) {

            values.put(MovieEntry.COL_IS_POPULAR, "1");
        }

        if(isTopRated) {

            values.put(MovieEntry.COL_IS_TOP_RATED, "1");
        }

        if(isNowPlaying) {

            values.put(MovieEntry.COL_IS_NOW_PLAYING, "1");
        }

        if(isUpcoming) {

            values.put(MovieEntry.COL_IS_UPCOMING, "1");
        }

        if(isFavorite) {

            values.put(MovieEntry.COL_IS_FAVORITE, "1");
        }

        return values;
    }
}
