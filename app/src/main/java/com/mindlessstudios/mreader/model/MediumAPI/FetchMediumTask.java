package com.mindlessstudios.mreader.model.MediumAPI;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMediumTask extends AsyncTask<String, Void, JSONObject> {

    private String LOG_TAG = FetchMediumTask.class.getSimpleName();

    protected Medium.Callback mCallback;
    protected boolean success = false;

    public FetchMediumTask(Medium.Callback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        try {
            URL url = new URL(params[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Append to the buffer
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            String jsonString = this.removeToken(buffer.toString());

            if (jsonString == null) {
                return null;
            }

            this.success = true;
            return new JSONObject(jsonString);

        } catch (Exception e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }

    /**
     * Removes the CSFR token from the beginning of the string
     * @param rawJson The raw string returned
     * @return Proper json string
     */
    private String removeToken(String rawJson) {
        String token = "])}while(1);</x>";

        if (rawJson.startsWith(token)) {
            return rawJson.substring(token.length());
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject obj) {
        if (obj != null && this.success) {
            mCallback.onBack(this.success, obj);
        }
    }
}