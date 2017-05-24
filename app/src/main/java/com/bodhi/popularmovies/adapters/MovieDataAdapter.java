package com.bodhi.popularmovies.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
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

        @BindView(R.id.info)
        public LinearLayout info;




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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MovieData curr = mMovies.get(position);

        com.squareup.picasso.Callback callback = new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.poster.getDrawable();
                Palette.from(bitmapDrawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette p) {
                        Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                        if (vibrantSwatch != null) {
                            holder.info.setBackgroundColor(vibrantSwatch.getRgb());
                            holder.title.setTextColor(vibrantSwatch.getTitleTextColor());
                            holder.rating.setTextColor(vibrantSwatch.getTitleTextColor());
                        }

                    }
                });

            }

            @Override
            public void onError() {

            }
        };


        if (curr.isFile) {
            Picasso.with(activityContext).load(new File(curr.posterURI)).error(R.drawable.ic_face_black_24dp).into(holder.poster, callback);
        } else {
            Picasso.with(activityContext).load(curr.posterURI).error(R.drawable.ic_face_black_24dp).into(holder.poster, callback);

        }


        holder.title.setText(curr.title);
        holder.rating.setText(Double.toString(curr.rating));

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


}
