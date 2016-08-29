package com.bodhi.popularmovies.data;

import com.google.gson.annotations.SerializedName;


import java.util.List;

/**
 * Created by gau on 6/19/2016.
 */
public class MovieGenreCollection {

    @SerializedName("genres")
    public List<MovieGenre> genres;


}
