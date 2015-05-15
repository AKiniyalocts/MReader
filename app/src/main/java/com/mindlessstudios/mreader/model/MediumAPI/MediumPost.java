package com.mindlessstudios.mreader.model.MediumAPI;

import android.net.Uri;

import java.util.Date;

/**
 * Created by James on 5/9/2015.
 */
public class MediumPost extends Medium {
    final static String RESOURCE = "posts";

    /**
     * Fetches posts
     *
     * @param limit The number of posts to return
     * @param date  The last date to check
     */
    public void getPosts(int limit, Date date, Callback callback) {
        final String LIMIT_PARAM = "limit";
        final String DATE_PARAM = "to";
        final String SOURCE_PARAM = "source";

        // Build the url
        Uri builtUri = Uri.parse(MEDIUM_BASE_URL + RESOURCE).buildUpon()
                .appendQueryParameter(LIMIT_PARAM, Integer.toString(limit))
                .appendQueryParameter(DATE_PARAM, Long.toString(date.getTime()))
                .appendQueryParameter(SOURCE_PARAM, "feed")
                .build();

        FetchMediumTask task = new FetchMediumTask(callback);
        task.execute(builtUri.toString());
    }
}
