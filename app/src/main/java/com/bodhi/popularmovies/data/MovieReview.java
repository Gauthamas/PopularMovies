package com.bodhi.popularmovies.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gau on 6/16/2016.
 */
public class MovieReview {

    @SerializedName("author")
    public String author;

    @SerializedName("content")
    public String content;
}
