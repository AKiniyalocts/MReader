package com.mindlessstudios.mreader.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mindlessstudios.mreader.R;
import com.mindlessstudios.mreader.model.MediumAPI.Medium;
import com.mindlessstudios.mreader.model.MediumAPI.MediumPost;

import org.json.JSONObject;

import java.util.Date;


public class MainActivity extends Activity {

    private static final String LOG_TAG = "MReader";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MediumPost().getPosts(25, new Date(), new Medium.Callback() {
            @Override
            public void onBack(boolean success, JSONObject data) {
                Log.v(LOG_TAG, data.toString());
            }
        });
    }
}
