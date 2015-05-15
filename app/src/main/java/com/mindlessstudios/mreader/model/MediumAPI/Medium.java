package com.mindlessstudios.mreader.model.MediumAPI;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;

/**
 * Implementation of the medium api
 */
abstract public class Medium {

    protected final static String MEDIUM_BASE_URL = "https://medium.com/_/api/";

    public interface Callback {
        public void onBack(boolean success, JSONObject data);
    }
}
