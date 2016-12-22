package com.example.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.async.MovieApiTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static String LOG_TAG = "MOVIES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        MovieApiTask task = new MovieApiTask() {

            @Override
            protected void onPostExecute(String response) {

                Log.v(LOG_TAG, response);
            }
        };

        // /popular
        // /top_rated
        // /now_playing
        // /upcoming
        // /{movie_id}

        task.execute("popular");
    }
}
