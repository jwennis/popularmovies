package com.example.android.popularmovies;

import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.async.MovieApiTask;
import com.example.android.popularmovies.ui.PosterAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = "MOVIES";

    private static final String PARAM_FILTER = "PARAM_FILTER";

    private static final int MOVIE_LOADER_ID = 0x13;

    private PosterAdapter mAdapter;
    private String mFilter;

    @BindView(R.id.discover_poster_grid)
    GridView poster_grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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

        String[] projection = null;
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
                // TODO: orderBy date, nearest first

                break;
            }

            case Movie.PARAM_FAVORITES: {

                selection = MovieEntry.COL_IS_FAVORITE + " = ?";
                // TODO: orderBy date favorited, newest first

                break;
            }
        }

        return new CursorLoader(this, MovieEntry.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {

        if(loader.getId() != MOVIE_LOADER_ID) { return; }

        if(data.getCount() > 0) {

            if(mAdapter == null) {

                mAdapter = new PosterAdapter(this, data);
                poster_grid.setAdapter(mAdapter);

            } else {

                mAdapter.swapCursor(data);
            }

            poster_grid.smoothScrollToPosition(0);

        } else {

            if(mFilter.equals(Movie.PARAM_FAVORITES)) {

                // TODO: no favorites message

                onLoaderReset(loader);

            } else {

                fetchMovies();
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        if(loader.getId() == MOVIE_LOADER_ID) {

            if (mAdapter != null) {

                mAdapter.swapCursor(null);
            }
        }
    }


    private void reload() {

        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
    }


    private void fetchMovies() {

        MovieApiTask task = new MovieApiTask(mFilter) {

            @Override
            protected void onPostExecute(List<Movie> movies) {

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
