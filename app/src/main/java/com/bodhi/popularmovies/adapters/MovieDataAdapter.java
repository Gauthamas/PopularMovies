package com.bodhi.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bodhi.popularmovies.data.MovieData;
import com.bodhi.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gau on 4/24/2016.
 */
public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ViewHolder> {

    Context activityContext;
    List<MovieData> mMovies;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movietitle)
        public TextView title;

        @BindView(R.id.ratingTitle)
        public TextView rating;

        @BindView(R.id.movie_poster)
        public ImageView poster;


        public ViewHolder(CardView v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    public MovieDataAdapter(Context context, List<MovieData> movies) {

        activityContext = context;
        mMovies = movies;
    }


    @Override
    public MovieDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_griditem, parent, false);
        ViewHolder vh = new ViewHolder((CardView) v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieData curr = mMovies.get(position);
        if (curr.isFile) {
            Picasso.with(activityContext).load(new File(curr.posterURI)).error(R.drawable.ic_face_black_24dp).into(holder.poster);
        } else {
            Picasso.with(activityContext).load(curr.posterURI).error(R.drawable.ic_face_black_24dp).into(holder.poster);
        }

        holder.title.setText(curr.title);
        holder.rating.setText(Double.toString(curr.rating));

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


}
