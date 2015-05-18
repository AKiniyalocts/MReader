package com.mindlessstudios.mreader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mindlessstudios.mreader.R;

/**
 * View holder for displaying posts
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    protected TextView title;

    public PostViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.post_title);
    }
}
