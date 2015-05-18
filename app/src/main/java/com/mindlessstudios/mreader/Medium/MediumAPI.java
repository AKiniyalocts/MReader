package com.mindlessstudios.mreader.Medium;

import org.json.JSONObject;

/**
 * Base class for all Medium API endpoints
 */
abstract public class MediumAPI {

    protected final static String MEDIUM_BASE_URL = "https://medium.com/_/api/";

    /**
     * API constants
     */
    protected final static String MEDIUM_FEED_SOURCE = "feed";

    /**
     * Callback for medium calls
     */
    public interface Callback {
        void onBack(boolean success, JSONObject data);
    }
}
