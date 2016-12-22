package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "

                + MovieContract.MovieEntry.TABLE_MOVIE + " ("

                + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + MovieEntry.COL_TMDB_ID + " INTEGER UNIQUE NOT NULL, "
                + MovieEntry.COL_TITLE + " TEXT NOT NULL, "
                + MovieEntry.COL_RELEASE_DATE + " TEXT NOT NULL, "
                + MovieEntry.COL_OVERVIEW + " TEXT NOT NULL, "
                + MovieEntry.COL_POSTER_PATH + " TEXT NOT NULL, "
                + MovieEntry.COL_BACKDROP_PATH + " TEXT, "
                + MovieEntry.COL_VOTE_COUNT + " INTEGER, "
                + MovieEntry.COL_VOTE_AVERAGE + " REAL, "
                + MovieEntry.COL_POPULARITY + " REAL, "
                + MovieEntry.COL_GENRES + " TEXT, "

                + MovieEntry.COL_IMDB_ID + " TEXT, "
                + MovieEntry.COL_TAGLINE + " TEXT, "
                + MovieEntry.COL_RUNTIME + " INTEGER, "
                + MovieEntry.COL_BUDGET + " INTEGER , "
                + MovieEntry.COL_REVENUE + " INTEGER , "

                + MovieEntry.COL_IS_POPULAR + " INTEGER NOT NULL, "
                + MovieEntry.COL_IS_TOP_RATED + " INTEGER NOT NULL, "
                + MovieEntry.COL_IS_FAVORITE + " INTEGER NOT NULL"

                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_MOVIE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MovieContract.MovieEntry.TABLE_MOVIE + "'");

        onCreate(db);
    }
}
