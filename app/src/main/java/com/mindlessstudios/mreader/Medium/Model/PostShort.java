package com.mindlessstudios.mreader.Medium.Model;

import java.io.Serializable;

/**
 * Model for shortened posts
 * (Like the small quibs being displayed on the front list)
 */
public class PostShort implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    public PostShort(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
