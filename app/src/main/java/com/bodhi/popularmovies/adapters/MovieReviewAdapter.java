package com.bodhi.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bodhi.popularmovies.R;
import com.bodhi.popularmovies.data.MovieReview;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gau on 6/17/2016.
 */
public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {
    Context activityContext;
    List<MovieReview> mMovies;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_review_content)
        public TextView item;

        @BindView(R.id.movie_review_author)
        public TextView author;

        public ViewHolder(LinearLayout v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    public MovieReviewAdapter(Context context, List<MovieReview> movies) {

        activityContext = context;
        mMovies = movies;
    }


    @Override
    public MovieReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_item, parent, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieReview curr = mMovies.get(position);
        holder.item.setText(curr.content);
        holder.author.setText(curr.author);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
