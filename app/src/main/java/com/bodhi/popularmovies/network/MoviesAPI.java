package com.bodhi.popularmovies.network;

import com.bodhi.popularmovies.data.MovieDataCollection;
import com.bodhi.popularmovies.data.MovieGenreCollection;
import com.bodhi.popularmovies.data.MovieReviewCollection;
import com.bodhi.popularmovies.data.MovieTrailerCollection;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by gau on 6/15/2016.
 */
public interface MoviesAPI {

    @GET("/3/movie/{criteria}")
    public void getMovies(@Path("criteria") String criteria, @Query("api_key") String api_key, @Query("page") String page, Callback<MovieDataCollection> response);

    @GET("/3/movie/{id}/reviews")
    public void getReviews(@Path("id") String id, @Query("api_key") String api_key, Callback<MovieReviewCollection> response);

    @GET("/3/movie/{id}/videos")
    public void getTrailers(@Path("id") String id, @Query("api_key") String api_key, Callback<MovieTrailerCollection> response);

    @GET("/3/genre/movie/list")
    public void getGenres(@Query("api_key") String api_key, Callback<MovieGenreCollection> response);


}
