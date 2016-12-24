package com.example.android.popularmovies.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;


public class Movie implements Parcelable {

    public static final String PARAM_MOVIE_PARCEL = "PARAM_MOVIE_PARCEL";

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
    private String genreString;

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

    private Trailer trailers;
    private Review reviews;


    public Movie() {

    }


    public Movie(Cursor data) {

        id = data.getInt(data.getColumnIndex(MovieEntry.COL_TMDB_ID));
        title = data.getString(data.getColumnIndex(MovieEntry.COL_TITLE));
        release_date = data.getString(data.getColumnIndex(MovieEntry.COL_RELEASE_DATE));
        overview = data.getString(data.getColumnIndex(MovieEntry.COL_OVERVIEW));
        poster_path = data.getString(data.getColumnIndex(MovieEntry.COL_POSTER_PATH));
        backdrop_path = data.getString(data.getColumnIndex(MovieEntry.COL_BACKDROP_PATH));
        vote_count = data.getInt(data.getColumnIndex(MovieEntry.COL_VOTE_COUNT));
        vote_average = data.getDouble(data.getColumnIndex(MovieEntry.COL_VOTE_AVERAGE));
        popularity = data.getDouble(data.getColumnIndex(MovieEntry.COL_POPULARITY));
        genreString = data.getString(data.getColumnIndex(MovieEntry.COL_GENRES));

        int col_imdb_id = data.getColumnIndex(MovieEntry.COL_IMDB_ID);

        if(col_imdb_id > -1) {

            imdb_id = data.getString(col_imdb_id);
        }

        int col_tagline = data.getColumnIndex(MovieEntry.COL_TAGLINE);

        if(col_tagline > -1) {

            tagline = data.getString(col_tagline);
        }

        int col_runtime = data.getColumnIndex(MovieEntry.COL_RUNTIME);

        if(col_runtime > -1) {

            runtime = data.getInt(col_runtime);
        }

        int col_budget = data.getColumnIndex(MovieEntry.COL_BUDGET);

        if(col_budget > -1) {

            budget = data.getInt(col_budget);
        }

        int col_revenue = data.getColumnIndex(MovieEntry.COL_REVENUE);

        if(col_revenue > -1) {

            revenue = data.getInt(col_revenue);
        }

        int col_is_popular = data.getColumnIndex(MovieEntry.COL_IS_POPULAR);

        if(col_is_popular > -1) {

            isPopular = data.getInt(col_is_popular) == 1;
        }

        int col_is_top_rated = data.getColumnIndex(MovieEntry.COL_IS_TOP_RATED);

        if(col_is_top_rated > -1) {

            isTopRated = data.getInt(col_is_top_rated) == 1;
        }

        int col_is_now_playing = data.getColumnIndex(MovieEntry.COL_IS_NOW_PLAYING);

        if(col_is_now_playing > -1) {

            isNowPlaying = data.getInt(col_is_now_playing) == 1;
        }

        int col_is_upcoming = data.getColumnIndex(MovieEntry.COL_IS_UPCOMING);

        if(col_is_upcoming > -1) {

            isUpcoming = data.getInt(col_is_upcoming) == 1;
        }

        int col_is_favorite = data.getColumnIndex(MovieEntry.COL_IS_FAVORITE);

        if(col_is_favorite > -1) {

            isFavorite = data.getInt(col_is_favorite) == 1;
        }
    }


    public Movie(Parcel in) {

        id = in.readInt();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        vote_count = in.readInt();
        vote_average = in.readDouble();
        popularity = in.readDouble();
        genreString = in.readString();
        imdb_id = in.readString();
        tagline = in.readString();
        runtime = in.readInt();
        budget = in.readInt();
        revenue = in.readInt();
        isPopular = in.readInt() == 1;
        isTopRated = in.readInt() == 1;
        isNowPlaying = in.readInt() == 1;
        isUpcoming = in.readInt() == 1;
        isFavorite = in.readInt() == 1;
    }


    public void print() {

        Log.v("MOVIES", "[ID] " + id);
        Log.v("MOVIES", "[TITLE] " + title);
        Log.v("MOVIES", "[RELEASED] " + release_date);
        Log.v("MOVIES", "[SYNOPSIS] " + overview);
        Log.v("MOVIES", "[POSTER] " + poster_path);
        Log.v("MOVIES", "[BACKDROP] " + backdrop_path);
        Log.v("MOVIES", "[# VOTES] " + vote_count);
        Log.v("MOVIES", "[RATING] " + vote_average);
        Log.v("MOVIES", "[POPULARITY] " + popularity);
        Log.v("MOVIES", "[GENRES] " + getGenres());

        if(imdb_id != null && !imdb_id.isEmpty()) {

            Log.v("MOVIES", "[IMDB ID] " + imdb_id);
        }

        if(tagline != null && !tagline.isEmpty()) {

            Log.v("MOVIES", "[TAGLINE] " + tagline);
        }

        if(runtime > 0) {

            Log.v("MOVIES", "[RUNTIME] " + runtime);
        }

        if(budget > 0) {

            Log.v("MOVIES", "[BUDGET] " + budget);
        }

        if(revenue > 0) {

            Log.v("MOVIES", "[REVENUE] " + revenue);
        }

        Log.v("MOVIES", "[POPULAR?] " + isPopular);
        Log.v("MOVIES", "[TOP RATED?] " + isTopRated);
        Log.v("MOVIES", "[NOW PLAYING?] " + isNowPlaying);
        Log.v("MOVIES", "[UPCOMING?] " + isUpcoming);
        Log.v("MOVIES", "[FAVORITE?] " + isFavorite);
    }


    // Accessors


    public int getId() {

        return id;
    }


    public String getTitle() {

        return title;
    }


    public String getBackdropPath() {

        return backdrop_path;
    }


    public String getPosterPath() {

        return poster_path;
    }


    public String getSynopsis() {

        return overview;
    }


    public int getYear() {

        return Integer.parseInt(release_date.substring(0, 4));
    }


    public double getRating() {

        return vote_average;
    }


    public int getNumVotes() {

        return vote_count * 10;
    }


    public String getGenres() {

        if(genreString != null && !genreString.isEmpty()) {

            return genreString;
        }

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

        return genreString =  builder.toString();
    }


    public List<Trailer> getTrailers() {

        return trailers.get();
    }


    public List<String> getReviews() {

        return reviews.get();
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


    // Parcelable


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {

            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {

            return new Movie[i];
        }
    };


    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(id);
        out.writeString(title);
        out.writeString(release_date);
        out.writeString(overview);
        out.writeString(poster_path);
        out.writeString(backdrop_path);
        out.writeInt(vote_count);
        out.writeDouble(vote_average);
        out.writeDouble(popularity);
        out.writeString(getGenres());
        out.writeString(imdb_id != null && !imdb_id.isEmpty() ? imdb_id : "");
        out.writeString(tagline != null && !tagline.isEmpty() ? tagline : "");
        out.writeInt(runtime);
        out.writeInt(budget);
        out.writeInt(revenue);
        out.writeInt(isPopular ? 1 : 0);
        out.writeInt(isTopRated ? 1 : 0);
        out.writeInt(isNowPlaying ? 1 : 0);
        out.writeInt(isUpcoming ? 1 : 0);
        out.writeInt(isFavorite ? 1 : 0);
    }


    @Override
    public int describeContents() {

        return 0;
    }


    public class Trailer {

        private List<Trailer> youtube;
        private String name;
        private String source;


        public List<Trailer> get() {

            return youtube;
        }


        public String getName() {

            return name;
        }


        public String getSource() {

            return source;
        }
    }


    public class Review {

        private List<Review> results;
        private String content;


        public List<String> get() {

            List<String> all = new ArrayList<>();

            for(Review review : results) {

                all.add(review.getContent());
            }

            return all;
        }


        public String getContent() {

            return content;
        }
    }
}
