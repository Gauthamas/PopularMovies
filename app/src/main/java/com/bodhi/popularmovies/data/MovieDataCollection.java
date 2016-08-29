package com.bodhi.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gau on 6/16/2016.
 */
public class MovieDataCollection {

    @SerializedName("results")
    public List<MovieData> result;

}
