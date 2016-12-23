package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {

            mMovie = getIntent().getExtras().getParcelable(Movie.PARAM_MOVIE_PARCEL);
        }

    }
}