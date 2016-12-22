package com.example.android.popularmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.async.MovieApiTask;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieContract.MovieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String LOG_TAG = "MOVIES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(MovieEntry.CONTENT_URI, null, null,null,  null);

        Log.v(LOG_TAG, DatabaseUtils.dumpCursorToString(cursor));

        //init();
    }

    private void init() {

        MovieApiTask task = new MovieApiTask("popular") {

            @Override
            protected void onPostExecute(List<Movie> movies) {

                Log.v(LOG_TAG, "# movies = " + movies.size());

                ContentValues[] values = new ContentValues[ movies.size() ];

                for(int i = 0; i < movies.size(); i++) {

                    values[i] = movies.get(i).getValues();
                }

                getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, values);
            }
        };
    }
}
