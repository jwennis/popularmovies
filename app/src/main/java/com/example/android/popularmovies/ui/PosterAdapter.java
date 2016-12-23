package com.example.android.popularmovies.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import butterknife.ButterKnife;
import butterknife.BindString;
import butterknife.BindView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieContract.MovieEntry;


public class PosterAdapter extends CursorAdapter {

    @BindView(R.id.poster)
    ImageView poster;

    @BindString(R.string.poster_root)
    String POSTER_ROOT;


    public PosterAdapter(Context context, Cursor data) {

        super(context, data, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item_poster, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor data) {

        ButterKnife.bind(this, view);

        Movie movie = new Movie(data);

        int tIndex = data.getColumnIndex(MovieEntry.COL_TITLE);
        final String title = data.getString(tIndex);

        int pIndex = data.getColumnIndex(MovieEntry.COL_POSTER_PATH);

        Glide.with(context)
                .load(POSTER_ROOT + data.getString(pIndex))
                .placeholder(R.drawable.poster_placeholder)
                .centerCrop()
                .into(poster);
    }
}
