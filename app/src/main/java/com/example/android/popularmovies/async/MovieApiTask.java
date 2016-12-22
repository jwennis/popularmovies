package com.example.android.popularmovies.async;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.popularmovies.BuildConfig;

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


public class MovieApiTask extends AsyncTask<String, Void, String> {

    final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String PARAM_LANGUAGE = "language";
    final static String PARAM_REGION = "region";
    final static String PARAM_PAGE = "page";
    final static String PARAM_APPEND = "append_to_response";
    final static String PARAM_API_KEY = "api_key";

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer;
        JSONArray results = null;

        Uri.Builder uri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(params[0])
                .appendQueryParameter(PARAM_LANGUAGE, "en-US")
                .appendQueryParameter(PARAM_REGION, "US")
                .appendQueryParameter(PARAM_PAGE, "1")
                .appendQueryParameter(PARAM_APPEND, "trailers,reviews")
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY);

        try {

            URL requestUrl =  new URL(uri.build().toString());
            InputStream stream;
            String response, line;

            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if((stream  = connection.getInputStream()) == null) {

                return null;
            }

            reader = new BufferedReader(new InputStreamReader(stream));
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {

                buffer.append(line);
            }

            if((response = buffer.toString()).length() > 0) {

                results = new JSONObject(response).getJSONArray("results");
            }

        } catch(MalformedURLException e) {

            e.printStackTrace();

        } catch(JSONException e) {

            e.printStackTrace();

        } catch(IOException e) {

            e.printStackTrace();

        } finally {

            if(connection != null) {

                connection.disconnect();
            }

            if(reader != null) {

                try {

                    reader.close();

                } catch(final IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return results != null ? results.toString() : null;
    }
}
