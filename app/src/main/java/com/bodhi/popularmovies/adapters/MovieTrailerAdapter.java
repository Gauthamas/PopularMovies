package com.bodhi.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.bodhi.popularmovies.data.MovieTrailer;
import com.bodhi.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gau on 4/24/2016.
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    Context activityContext;
    List<MovieTrailer> mMovies;
    private static final String YOUTUBE_PREFIX = "http://img.youtube.com/vi/";
    private static final String YOUTUBE_SUFFIX = "/0.jpg";


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;

        public ViewHolder(ImageView v) {
            super(v);
            mImageView = v;
        }
    }


    public MovieTrailerAdapter(Context context, List<MovieTrailer> movies) {

        activityContext = context;
        mMovies = movies;
    }


    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_trailer_item, parent, false);
        ViewHolder vh = new ViewHolder((ImageView) v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(activityContext).load(YOUTUBE_PREFIX + mMovies.get(position).key + YOUTUBE_SUFFIX).error(R.drawable.ic_face_black_24dp).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


}
