package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;

import java.util.List;

public class MovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = sBuildUriMatcher();

    private static final int MOVIE = 100;
    private static final int MOVIE_BY_ID = 101;
    private static final int MOVIE_WITH_FILTER = 102;

    private MovieDbHelper mDbHelper;


    private static UriMatcher sBuildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieEntry.TABLE_MOVIE, MOVIE);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieEntry.TABLE_MOVIE + "/#", MOVIE_BY_ID);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieEntry.TABLE_MOVIE + "/*", MOVIE_WITH_FILTER);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mDbHelper = new MovieDbHelper(getContext());

        return true;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {

        switch(sUriMatcher.match(uri)) {

            case MOVIE: {

                return MovieEntry.CONTENT_DIR_TYPE;
            }

            case MOVIE_BY_ID: {

                return MovieEntry.CONTENT_ITEM_TYPE;
            }

            case MOVIE_WITH_FILTER: {

                return MovieEntry.CONTENT_ITEM_TYPE;
            }

            default: {

                throw new UnsupportedOperationException("Unknown URI: " + uri);
            }
        }
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch(sUriMatcher.match(uri)) {

            case MOVIE: {

                // Pass all parameters as is

                break;
            }

            case MOVIE_BY_ID: {

                selection = MovieEntry.COL_TMDB_ID + " = ?";
                selectionArgs = new String[] { Long.toString(ContentUris.parseId(uri)) };

                break;
            }

            case MOVIE_WITH_FILTER: {

                switch(uri.getPathSegments().get(1)) {

                    case MovieEntry.COL_IS_POPULAR: {

                        sortOrder = MovieEntry.COL_POPULARITY + " DESC";

                        break;
                    }

                    case MovieEntry.COL_IS_TOP_RATED: {

                        sortOrder = MovieEntry.COL_VOTE_AVERAGE + " DESC";

                        break;
                    }

                    case MovieEntry.COL_IS_FAVORITE: {

                        selection = MovieEntry.COL_IS_FAVORITE + " > ?";
                        selectionArgs = new String[] { "0" };
                        sortOrder = MovieEntry.COL_IS_FAVORITE + " DESC";

                        break;
                    }

                    default: {

                        String message = String.format("Unknown filter [%1$s] in %2$s",
                                uri.getPathSegments().get(1), uri);

                        throw new UnsupportedOperationException(message);
                    }
                }

                break;
            }

            default: {

                throw new UnsupportedOperationException("Unknown URI: " + uri);
            }
        }

        return mDbHelper.getReadableDatabase().query(MovieEntry.TABLE_MOVIE,
                projection, selection, selectionArgs, null, null, sortOrder);
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Uri returnUri;

        switch (sUriMatcher.match(uri)) {

            case MOVIE: {

                long _id = db.insert(MovieEntry.TABLE_MOVIE, null, values);

                if (_id > 0) {

                    returnUri = MovieEntry.buildMovieUri(_id);

                } else {

                    throw new SQLException("Failed to insert row into: " + uri);
                }

                break;
            }

            case MOVIE_BY_ID: {

                throw new UnsupportedOperationException("Insert MOVIE_BY_ID is unsupported");
            }

            case MOVIE_WITH_FILTER: {

                throw new UnsupportedOperationException("Insert MOVIE_WITH_FILTER is unsupported");
            }

            default: {

                throw new UnsupportedOperationException("Unknown URI: " + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case MOVIE: {

                db.beginTransaction();

                int numInserted = 0;
                int numUpdated = 0;

                try {

                    for(ContentValues itemValues : values) {

                        if (itemValues == null) {

                            throw new IllegalArgumentException("Attempted insert contains null data.");
                        }

                        long _id = -1;

                        try {

                            _id = db.insertOrThrow(MovieEntry.TABLE_MOVIE, null, itemValues);

                        } catch(SQLiteConstraintException e) {

                            // Entry already exists in the database
                        }

                        if (_id != -1) {

                            numInserted++;
                        }
                    }

                    if(numInserted + numUpdated > 0) {

                        db.setTransactionSuccessful();
                    }

                } finally {

                    db.endTransaction();
                }

                if(numInserted + numUpdated > 0) {

                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return numInserted;
            }

            case MOVIE_BY_ID: {

                throw new UnsupportedOperationException("Bulk insert MOVIE_BY_ID is unsupported.");
            }

            case MOVIE_WITH_FILTER: {

                throw new UnsupportedOperationException("Bulk insert MOVIE_WITH_FILTER is unsupported.");
            }

            default: {

                return super.bulkInsert(uri, values);
            }
        }
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int numUpdated = 0;

        if (values == null) {

            throw new IllegalArgumentException("Attempted update contains null data");
        }

        switch(sUriMatcher.match(uri)) {

            case MOVIE: {

                numUpdated = db.update(MovieEntry.TABLE_MOVIE, values, selection, selectionArgs);

                break;
            }

            case MOVIE_BY_ID: {

                numUpdated = db.update(MovieEntry.TABLE_MOVIE,
                        values, MovieEntry.COL_TMDB_ID + " = ?",
                        new String[] { Long.toString(ContentUris.parseId(uri)) });

                break;
            }

            case MOVIE_WITH_FILTER: {

                throw new UnsupportedOperationException("Update MOVIE_WITH_FILTER is unsupported.");
            }

            default: {

                throw new UnsupportedOperationException("Unknown URI: " + uri);
            }
        }

        if (numUpdated > 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int numDeleted;

        switch(sUriMatcher.match(uri)) {

            case MOVIE: {

                numDeleted = db.delete(MovieEntry.TABLE_MOVIE, selection, selectionArgs);

                break;
            }

            case MOVIE_BY_ID: {

                numDeleted = db.delete(MovieEntry.TABLE_MOVIE,
                        MovieEntry.COL_TMDB_ID + " = ?",
                        new String[]{ Long.toString(ContentUris.parseId(uri)) });

                break;
            }

            case MOVIE_WITH_FILTER: {

                throw new UnsupportedOperationException("Delete MOVIE_WITH_FILTER is unsupported.");
            }

            default: {

                throw new UnsupportedOperationException("Unknown URI: " + uri);
            }
        }

        return numDeleted;
    }
}
