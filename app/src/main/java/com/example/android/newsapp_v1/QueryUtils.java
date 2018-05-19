package com.example.android.newsapp_v1;


import android.util.Log;

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
import java.nio.charset.Charset;

import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving news feed from Guardian.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<News> extractNews(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        ArrayList<News> newses = extractFeatureFromJson(jsonResponse);
        return newses;
    }

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<News> extractFeatureFromJson(String JSON_RESPONSE) {

        // Create an empty ArrayList that we can start adding news to
        ArrayList<News> newses = new ArrayList<>();

        // Try to parse the JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // build up a list of News objects with the corresponding data.
            JSONObject reader = new JSONObject(JSON_RESPONSE);
            JSONObject response = reader.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject story = results.getJSONObject(i);
                String sectionName = story.getString("sectionName");
                String webTitle = story.getString("webTitle");
                String webUrl = story.getString("webUrl");
                String webPublicationDate = story.getString("webPublicationDate");
                String author = "";
                try{
                    author = story.getString("webAuthor");
                }catch (JSONException e){
                    Log.i("QueryUtils", "There is no author...", e);
                    }

                News thisNews = new News(sectionName, webTitle, webUrl, webPublicationDate, author);

                Log.e("NEWS:", thisNews.toString());

                newses.add(thisNews);
            }

        } catch (JSONException e) {
            // Print a log message with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }

        // Return the list of news
        return newses;

    }
}