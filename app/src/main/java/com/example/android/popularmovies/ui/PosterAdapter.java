package com.example.android.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
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

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.MainActivity;
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

        final Movie movie = new Movie(data);

        Glide.with(context)
                .load(POSTER_ROOT + movie.getPosterPath())
                .placeholder(R.drawable.poster_placeholder)
                .centerCrop()
                .into(poster);

        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Context context = view.getContext();

                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(Movie.PARAM_MOVIE_PARCEL, movie);

                context.startActivity(detailIntent);
            }
        });
    }
}
