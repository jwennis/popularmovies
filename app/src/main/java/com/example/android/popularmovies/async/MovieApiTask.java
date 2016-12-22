package com.example.android.popularmovies.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.data.Movie;


public class MovieApiTask extends AsyncTask<String, Void, List<Movie>> {

    final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String PARAM_LANGUAGE = "language";
    final static String PARAM_REGION = "region";
    final static String PARAM_PAGE = "page";
    final static String PARAM_APPEND = "append_to_response";
    final static String PARAM_API_KEY = "api_key";

    private HttpURLConnection mConnection;
    private BufferedReader mReader;

    public MovieApiTask(String requestPath) {

        super();

        mConnection = null;
        mReader = null;

        /*
            /popular
            /top_rated
            /now_playing
            /upcoming
            /{movie_id}
        */

        execute(requestPath);
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        JSONArray jsonResults = null;

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

                    jsonResults = new JSONObject(response).getJSONArray("results");
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

        if(jsonResults != null) {

            return new Gson().fromJson(jsonResults.toString(),
                    new TypeToken<List<Movie>>(){}.getType());
        } else {

            return null;
        }
    }

    private HttpURLConnection buildUrlConnection(String requestPath) throws IOException {

        Uri.Builder uri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(requestPath)
                .appendQueryParameter(PARAM_LANGUAGE, "en-US")
                .appendQueryParameter(PARAM_REGION, "US")
                .appendQueryParameter(PARAM_PAGE, "1")
                //.appendQueryParameter(PARAM_APPEND, "trailers,reviews")
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY);

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
