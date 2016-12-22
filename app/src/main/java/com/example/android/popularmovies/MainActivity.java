package com.example.android.popularmovies;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.async.MovieApiTask;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = "MOVIES";

    private static final String PARAM_FILTER = "PARAM_FILTER";

    private static final int MOVIE_LOADER_ID = 0x13;

    private String mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {

            mFilter = savedInstanceState.getString(PARAM_FILTER);

        } else {

            mFilter = Movie.PARAM_POPULAR;
        }

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(PARAM_FILTER, mFilter);

        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_popular: {

                mFilter = Movie.PARAM_POPULAR;

                reload();

                return true;
            }

            case R.id.action_top_rated: {

                mFilter = Movie.PARAM_TOP_RATED;

                reload();

                return true;
            }

            case R.id.action_now_playing: {

                mFilter = Movie.PARAM_NOW_PLAYING;

                reload();

                return true;
            }

            case R.id.action_upcoming: {

                mFilter = Movie.PARAM_UPCOMING;

                reload();

                return true;
            }

            case R.id.action_favorites: {

                mFilter = Movie.PARAM_FAVORITES;

                reload();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = new String[] { MovieEntry._ID, MovieEntry.COL_POSTER_PATH, MovieEntry.COL_TITLE,
                MovieEntry.COL_IS_POPULAR,MovieEntry.COL_IS_TOP_RATED,MovieEntry.COL_IS_NOW_PLAYING,MovieEntry.COL_IS_UPCOMING };

        String[] selectionArgs = new String[] { "1" };
        String selection = null, orderBy = null;

        switch(mFilter) {

            case Movie.PARAM_POPULAR: {

                selection = MovieEntry.COL_IS_POPULAR + " = ?";

                break;
            }

            case Movie.PARAM_TOP_RATED: {

                selection = MovieEntry.COL_IS_TOP_RATED + " = ?";
                orderBy = MovieEntry.COL_VOTE_AVERAGE + " DESC";

                break;
            }

            case Movie.PARAM_NOW_PLAYING: {

                selection = MovieEntry.COL_IS_NOW_PLAYING + " = ?";

                break;
            }

            case Movie.PARAM_UPCOMING: {

                selection = MovieEntry.COL_IS_UPCOMING + " = ?";

                break;
            }

            case Movie.PARAM_FAVORITES: {

                selection = MovieEntry.COL_IS_FAVORITE + " = ?";

                break;
            }
        }

        return new CursorLoader(this, MovieEntry.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data.getCount() == 0) {

            if(mFilter == Movie.PARAM_FAVORITES) {

                // no favorites :(

            } else {

                fetchMovies();
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void reload() {

        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void fetchMovies() {

        MovieApiTask task = new MovieApiTask(mFilter) {

            @Override
            protected void onPostExecute(List<Movie> movies) {

                Log.v(LOG_TAG, "onPostExecute # movies = " + movies.size());

                ContentValues[] values = new ContentValues[ movies.size() ];

                for(int i = 0; i < movies.size(); i++) {

                    values[i] = movies.get(i).getValues();
                    values[i].put(mFilter, "1");
                }

                getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, values);

                reload();
            }
        };
    }
}
