package com.bodhi.popularmovies.ui;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bodhi.popularmovies.R;
import com.bodhi.popularmovies.adapters.GetMovieDataAdapter;
import com.bodhi.popularmovies.adapters.MovieReviewAdapter;
import com.bodhi.popularmovies.adapters.MovieTrailerAdapter;
import com.bodhi.popularmovies.data.MovieData;
import com.bodhi.popularmovies.data.MovieReview;
import com.bodhi.popularmovies.data.MovieReviewCollection;
import com.bodhi.popularmovies.data.MovieTrailer;
import com.bodhi.popularmovies.data.MovieTrailerCollection;
import com.bodhi.popularmovies.database.FavMovColumns;
import com.bodhi.popularmovies.database.FavMovProvider;
import com.bodhi.popularmovies.utils.Utils;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private static GetMovieDataAdapter mGetMovieDataAdapter = GetMovieDataAdapter.getMovieDataAdapter();

    private static MovieData md;

    private boolean isAlreadyPresent = false;

    private RecyclerView.Adapter movieAdapt;
    private ArrayList<MovieTrailer> movielist = new ArrayList<MovieTrailer>();

    LinearLayoutManager mLayoutManager;

    private RecyclerView.Adapter movieReviewAdapt;
    private ArrayList<MovieReview> moviereviewlist = new ArrayList<MovieReview>();

    LinearLayoutManager mReviewLayoutManager;


    private ShareActionProvider mShareActionProvider;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.rating_detail)
    TextView rating;

    @BindView(R.id.release_date)
    TextView release;

    @BindView(R.id.genre_detail)
    TextView genre;

    @BindView(R.id.overview)
    TextView synopsis;

    @BindView(R.id.total_audience)
    TextView audience;

    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.movie_poster)
    ImageView poster;

    @BindView(R.id.movie_trailers)
    RecyclerView gv;

    @BindView(R.id.movie_reviews)
    RecyclerView gvr;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static void setMovieData(MovieData mdata) {
        md = mdata;
    }


    Callback<MovieReviewCollection> responseReview = new Callback<MovieReviewCollection>() {
        @Override
        public void success(MovieReviewCollection mc, Response response) {

            List<MovieReview> movieReviews = mc.results;

            if (movieReviews != null) {
                moviereviewlist.addAll(movieReviews);
                movieReviewAdapt.notifyDataSetChanged();
            }


        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, "failure: " + error);

        }
    };

    Callback<MovieTrailerCollection> responseTrailer = new Callback<MovieTrailerCollection>() {
        @Override
        public void success(MovieTrailerCollection mc, Response response) {
            List<MovieTrailer> movieTrailers = mc.results;

            if (movieTrailers != null) {
                movielist.addAll(movieTrailers);
                movieAdapt.notifyDataSetChanged();
            }


        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, "failure: " + error);

        }
    };


    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);


        ButterKnife.bind(this, rootView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        gv.setLayoutManager(mLayoutManager);
        gv.setHasFixedSize(true);


        movieAdapt = new MovieTrailerAdapter(getActivity(), movielist);
        gv.setAdapter(movieAdapt);

        gv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MovieTrailer mv = movielist.get(position);

                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=" + mv.key)));
                    }
                })
        );


        mReviewLayoutManager = new LinearLayoutManager(getActivity());
        gvr.setLayoutManager(mReviewLayoutManager);
        gvr.setHasFixedSize(true);


        movieReviewAdapt = new MovieReviewAdapter(getActivity(), moviereviewlist);
        gvr.setAdapter(movieReviewAdapt);


        //Obtain the views


        MovieData mv = md;


        if (mv != null) {
            //Set content in views
            Cursor cr = null;
            try {
                cr = getActivity().getContentResolver().query(FavMovProvider.Movies.withId(md.id),
                        null, null, null, null);
                if (cr != null && cr.getCount() != 0) {
                    isAlreadyPresent = true;
                    fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
            } catch (
                    Exception e
                    ) {
                Log.e(TAG, "exception: " + e);

            } finally {
                cr.close();
            }


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertMovies();

                }
            });
            collapsingToolbar.setTitle(mv.title);
            rating.setText(Double.toString(mv.rating));
            release.setText(mv.release_date);
            synopsis.setText(mv.synopsis);
            genre.setText(mv.genres);
            audience.setText(Integer.toString(mv.vote_count));
            if (mv.isFile) {
                Picasso.with(getActivity()).load(new File(mv.backdrop_path))
                        .error(R.drawable.ic_face_black_24dp).into(imageView);
                Picasso.with(getActivity()).load(new File(mv.posterURI))
                        .error(R.drawable.ic_face_black_24dp).into(poster);

            } else {
                Picasso.with(getActivity()).load(mv.backdrop_path)
                        .error(R.drawable.ic_face_black_24dp).into(imageView);
                Picasso.with(getActivity()).load(mv.posterURI)
                        .error(R.drawable.ic_face_black_24dp).into(poster);
            }


            mGetMovieDataAdapter.getReviews(Double.toString(mv.id), responseReview);
            mGetMovieDataAdapter.getTrailers(Double.toString(mv.id), responseTrailer);


        }


        return rootView;

    }

    private void insertMovies() {


        if (!isAlreadyPresent) {
            saveImages();
            ContentValues cv = new ContentValues();
            cv.put(FavMovColumns.MOVIE_ID, md.id);
            cv.put(FavMovColumns.MOVIE_AUDIENCE, md.vote_count);
            cv.put(FavMovColumns.MOVIE_BACK_IMAGE, (String) null);
            cv.put(FavMovColumns.MOVIE_GENRE, md.genres);
            cv.put(FavMovColumns.MOVIE_NAME, md.title);
            cv.put(FavMovColumns.MOVIE_OVERVIEW, md.synopsis);
            cv.put(FavMovColumns.MOVIE_POSTER, (String) null);
            cv.put(FavMovColumns.MOVIE_RATING, md.rating);
            cv.put(FavMovColumns.MOVIE_RELEASE_DATE, md.release_date);
            cv.put(FavMovColumns.MOVIE_POSTER, getPosterPath());
            cv.put(FavMovColumns.MOVIE_BACK_IMAGE, getBackgroundPath());


            getActivity().getContentResolver().insert(FavMovProvider.Movies.FAV_MOVIES, cv);
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            isAlreadyPresent = true;


        } else {
            getActivity().getContentResolver().delete(FavMovProvider.Movies.withId(md.id),
                    null, null);
            isAlreadyPresent = false;
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
            deleteImages();

        }


    }

    private void deleteImages() {
        File file1 = new File(getBackgroundPath());
        File file2 = new File(getPosterPath());

        file1.delete();
        file2.delete();
    }

    private void saveImages() {

        Utils.imageDownload(getActivity(), md.backdrop_path, getBackgroundPath());
        Utils.imageDownload(getActivity(), md.posterURI, getPosterPath());
    }

    private String getBackgroundPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" +
                getString(R.string.image_folder) + "/" + "b" + Integer.toString(md.id) + ".jpg";
    }

    private String getPosterPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/" +
                getString(R.string.image_folder) + "/" + "p" + Integer.toString(md.id) + ".jpg";
    }

    private void setShareIntent(String msg) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.putExtra(Intent.EXTRA_SUBJECT, "This movie is dope!!");
        mShareActionProvider.setShareIntent(intent);
    }

}
