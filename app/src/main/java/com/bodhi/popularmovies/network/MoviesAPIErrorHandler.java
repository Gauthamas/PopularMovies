package com.bodhi.popularmovies.network;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gau on 6/16/2016.
 */
public class MoviesAPIErrorHandler implements ErrorHandler {
    protected final String TAG = getClass().getSimpleName();

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            Log.e(TAG, "Error:", cause);
        }
        return cause;

    }
}
