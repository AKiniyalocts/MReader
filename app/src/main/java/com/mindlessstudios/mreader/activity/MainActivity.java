package com.mindlessstudios.mreader.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.mindlessstudios.mreader.Medium.Model.PostShort;
import com.mindlessstudios.mreader.R;
import com.mindlessstudios.mreader.Service.PostService;

import java.util.Calendar;


public class MainActivity extends Activity {

    private static final String LOG_TAG = "MReader";

    private PostShortLoadedReceiver mPostShortLoadedReciever;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start the Recycler View


        // Register receiver
        this.mPostShortLoadedReciever = new PostShortLoadedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PostService.INTENT_PHOTO_SHORT_LOADED);
        registerReceiver(this.mPostShortLoadedReciever, filter);

        // Start service
        Intent intent = new Intent(this, PostService.class);
        intent.setAction(PostService.ACTION_GET_SHORT_POSTS);
        intent.putExtra(PostService.PARAM_GET_SHORT_POSTS_TRANSFORM, true);
        this.startService(intent);
    }

    private class PostShortLoadedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = null;

            if (!intent.hasExtra(PostService.EXTRA_PHOTO_SHORT)) {
                return;
            }

            String postShortsGson = intent.getStringExtra(PostService.EXTRA_PHOTO_SHORT);

            Gson gson = new Gson();

            PostShort[] posts = gson.fromJson(postShortsGson, PostShort[].class);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(this.mPostShortLoadedReciever);
        super.onDestroy();
    }
}
