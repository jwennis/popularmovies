package com.example.android.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.popularmovies.BuildConfig;

public class MovieContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_MOVIE = "movie";

        public static final String COL_TMDB_ID = "tmdb_id";
        public static final String COL_TITLE = "title";
        public static final String COL_RELEASE_DATE = "release_date";
        public static final String COL_OVERVIEW = "overview";
        public static final String COL_POSTER_PATH = "poster_path";
        public static final String COL_BACKDROP_PATH = "backdrop_path";
        public static final String COL_VOTE_COUNT = "vote_count";
        public static final String COL_VOTE_AVERAGE = "vote_average";
        public static final String COL_POPULARITY = "popularity";
        public static final String COL_GENRES = "genres";

        public static final String COL_IMDB_ID = "imdb_id";
        public static final String COL_TAGLINE = "tagline";
        public static final String COL_RUNTIME = "runtime";
        public static final String COL_BUDGET = "budget";
        public static final String COL_REVENUE = "revenue";

        public static final String COL_IS_POPULAR = "popular";
        public static final String COL_IS_TOP_RATED = "top_rated";
        public static final String COL_IS_FAVORITE = "favorite";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIE).build();
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIE;

        public static Uri buildMovieUri(long id) {

            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieUri(String path) {

            return CONTENT_URI.buildUpon().appendPath(path).build();
        }
    }

}
