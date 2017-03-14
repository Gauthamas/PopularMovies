package com.bodhi.popularmovies.adapters;

import com.bodhi.popularmovies.data.MovieDataCollection;
import com.bodhi.popularmovies.data.MovieGenreCollection;
import com.bodhi.popularmovies.data.MovieReviewCollection;
import com.bodhi.popularmovies.data.MovieTrailerCollection;
import com.bodhi.popularmovies.network.MoviesAPI;
import com.bodhi.popularmovies.network.MoviesAPIErrorHandler;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by gau on 6/15/2016.
 */
public class GetMovieDataAdapter {

    protected RestAdapter mRestAdapter;
    protected MoviesAPI mApi;

    static GetMovieDataAdapter sMovieDataAdapter;
    static final String MOVIE_URL = "http://api.themoviedb.org";
    //MOVIE_API key removed for confidentiality purposes
    static final String MOVIE_API = "8ec244a6354aa06b7c7a82112ae1e7d4";

    public GetMovieDataAdapter() {
        mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(MOVIE_URL)
                .setErrorHandler(new MoviesAPIErrorHandler())
                .build();
        mApi = mRestAdapter.create(MoviesAPI.class);
    }

    public void getMovieData(String criteria, String page, Callback<MovieDataCollection> response) {

        mApi.getMovies(criteria, MOVIE_API, page, response);

    }

    public void getReviews(String id, Callback<MovieReviewCollection> response) {

        mApi.getReviews(id, MOVIE_API, response);

    }

    public void getTrailers(String id, Callback<MovieTrailerCollection> response) {

        mApi.getTrailers(id, MOVIE_API, response);

    }

    public void getGenres(Callback<MovieGenreCollection> response) {

        mApi.getGenres(MOVIE_API, response);

    }

    public static GetMovieDataAdapter getMovieDataAdapter() {
        if (sMovieDataAdapter == null) {
            sMovieDataAdapter = new GetMovieDataAdapter();
        }
        return sMovieDataAdapter;
    }
}
