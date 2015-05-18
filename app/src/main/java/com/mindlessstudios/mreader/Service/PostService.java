package com.mindlessstudios.mreader.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindlessstudios.mreader.Medium.Medium;
import com.mindlessstudios.mreader.Medium.MediumAPI.Callback;
import com.mindlessstudios.mreader.Medium.Model.PostShort;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


/**
 * A Service for getting posts
 */
public class PostService extends IntentService {

    private static final String LOG_TAG = PostService.class.getSimpleName();

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_GET_SHORT_POSTS = "com.mindlessstudios.mreader.Service.action.GET_SHORT_POSTS";

    // Parameters
    public static final String PARAM_GET_SHORT_POSTS_TRANSFORM = "com.mindlessstudios.mreader.Service.action.GET_SHORT_POSTS.param.TRANSFORM";

    // Extra keys
    public static final String EXTRA_PHOTO_SHORT = "com.mindlessstudios.mreader.Service.action.EXTRA_PHOTO_SHORT";

    // Intent filters
    public static final String INTENT_PHOTO_SHORT_LOADED = "com.mindlessstudios.mreader.Service.action.INTENT_PHOTO_SHORT_LOADED";

    public PostService() {
        super("PostService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            if (action != null) {
                if (action.equals(ACTION_GET_SHORT_POSTS)) {
                    this.handleGetShortPosts(intent.hasExtra(PARAM_GET_SHORT_POSTS_TRANSFORM));
                }
            }
        }
    }

    /**
     * Grabs new posts from Medium API
     * @param transform Whether or not to transform the resulting object into
     */
    private void handleGetShortPosts(final boolean transform) {
        // Calculate a new date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        Medium.postAPI.getPosts(25, cal.getTime(), new Callback() {

            @Override
            public void onBack(boolean success, JSONObject data) {
                JSONArray array = new JSONArray();
                if (success && transform) {
                    ConvertToPostShort task = new ConvertToPostShort(INTENT_PHOTO_SHORT_LOADED);
                    task.execute(data);
                }
            }
        });
    }

    /**
     * Converts raw json GetPosts call to array of PostShort
     */
    private class ConvertToPostShort extends AsyncTask<JSONObject, Void, PostShort[]> {

        private final String mBroadcastIntent;

        public ConvertToPostShort(String broadcastIntent) {
            this.mBroadcastIntent = broadcastIntent;
        }

        @Override
        protected PostShort[] doInBackground(JSONObject... params) {
            return new PostShort[0];
        }

        @Override
        protected void onPostExecute(PostShort[] postShorts) {
            Intent intent = new Intent(INTENT_PHOTO_SHORT_LOADED);

            // Convert to gson
            Gson gson = new Gson();
            String postShortsGson = gson.toJson(postShorts);

            intent.putExtra(EXTRA_PHOTO_SHORT, postShortsGson);

            sendBroadcast(intent);
        }
    }
}
