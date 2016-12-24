package com.example.android.popularmovies.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.data.Movie;


public class MovieApiTask extends AsyncTask<String, Void, List<Movie>> {

    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_REGION = "region";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_APPEND = "append_to_response";
    private static final String PARAM_API_KEY = "api_key";

    private static final List<String> API_ENDPOINTS = Arrays.asList(Movie.PARAM_POPULAR,
            Movie.PARAM_TOP_RATED, Movie.PARAM_NOW_PLAYING, Movie.PARAM_UPCOMING);

    private HttpURLConnection mConnection;
    private BufferedReader mReader;


    public MovieApiTask(String requestPath) {

        super();

        execute(requestPath);
    }


    @Override
    protected List<Movie> doInBackground(String... params) {

        List<Movie> results = new ArrayList<>();

        try {

            InputStream stream;
            StringBuffer buffer;
            String response, line;

            mConnection = buildUrlConnection(params[0]);
            mConnection.setRequestMethod("GET");
            mConnection.connect();

            if((stream  = mConnection.getInputStream()) != null) {

                mReader = new BufferedReader(new InputStreamReader(stream));
                buffer = new StringBuffer();

                while ((line = mReader.readLine()) != null) {

                    buffer.append(line);
                }

                if((response = buffer.toString()).length() > 0) {

                    JSONObject json = new JSONObject(response);
                    String jsonString;
                    Type tokenType;

                    if(API_ENDPOINTS.indexOf(params[0]) > -1) {

                        jsonString = json.getJSONArray("results").toString();
                        tokenType = new TypeToken<List<Movie>>(){}.getType();

                        results = new Gson().fromJson(jsonString, tokenType);

                    } else {

                        jsonString = json.toString();
                        tokenType = new TypeToken<Movie>(){}.getType();

                        results.add((Movie) new Gson().fromJson(jsonString, tokenType));
                    }
                }
            }

        } catch(MalformedURLException e) {

            e.printStackTrace();

        } catch(JSONException e) {

            e.printStackTrace();

        } catch(IOException e) {

            e.printStackTrace();

        } finally {

            disconnect();
        }

        return results;
    }


    private HttpURLConnection buildUrlConnection(String requestPath) throws IOException {

        Uri.Builder uri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(requestPath);

        if (API_ENDPOINTS.indexOf(requestPath) > -1) {

            uri.appendQueryParameter(PARAM_REGION, "US");
            uri.appendQueryParameter(PARAM_LANGUAGE, "en-US");
            uri.appendQueryParameter(PARAM_PAGE, "1");

        } else {

            uri.appendQueryParameter(PARAM_APPEND, "trailers,reviews");
        }

        uri.appendQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY);

        return (HttpURLConnection) new URL(uri.build().toString()).openConnection();
    }


    private void disconnect() {

        if(mConnection != null) {

            mConnection.disconnect();
        }

        if(mReader != null) {

            try {

                mReader.close();

            } catch(final IOException e) {

                e.printStackTrace();
            }
        }
    }
}
