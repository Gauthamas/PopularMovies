package com.bodhi.popularmovies.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.bodhi.popularmovies.data.MovieData;
import com.bodhi.popularmovies.data.MovieDataCollection;
import com.bodhi.popularmovies.R;
import com.bodhi.popularmovies.adapters.GetMovieDataAdapter;
import com.bodhi.popularmovies.adapters.MovieDataAdapter;
import com.bodhi.popularmovies.data.MovieGenre;
import com.bodhi.popularmovies.data.MovieGenreCollection;
import com.bodhi.popularmovies.database.FavMovColumns;
import com.bodhi.popularmovies.database.FavMovProvider;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieShowcaseFragment extends Fragment {


    private final String TAG = getClass().getSimpleName();
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 15;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int scrollvalue = 0;
    private int page = 1;
    private int loadedpage = 0;


    private RecyclerView.Adapter movieAdapt;
    private ArrayList<MovieData> movielist = new ArrayList<MovieData>();
    private GridLayoutManager mLayoutManager;

    public interface ShowcaseCallback {
        /**
         * MovieDetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(MovieData md);
    }


    private static HashMap<Integer, String> genreMap = new HashMap<Integer, String>();


    private enum CATEGORIES {
        rating, popular, fav
    }

    private CATEGORIES currCategory = CATEGORIES.popular;

    static final String SORT_KIND = "sortKind";
    static final String MOVIE_LIST = "movieList";
    static final String PREV_TOT = "prevTot";
    static final String LOAD_STAT = "loadStat";
    static final String FIRST_VIS = "firstVis";
    static final String VIS_COUNT = "visCount";
    static final String TOT_COUNT = "totCount";
    static final String PAGE = "page";
    static final String LOAD_PAGE = "loadPage";
    static final String SCROLLTO = "scrollTo";

    private static GetMovieDataAdapter mGetMovieDataAdapter =
            GetMovieDataAdapter.getMovieDataAdapter();


    Callback<MovieDataCollection> response = new Callback<MovieDataCollection>() {
        @Override
        public void success(MovieDataCollection mc, Response response) {

            List<MovieData> movieDatas = mc.result;
            String posterPrefix = getString(R.string.poster_prefix);
            String backgroundPrefix = getString(R.string.background_prefix);
            for (MovieData m : movieDatas) {
                m.posterURI = posterPrefix + m.posterURI;
                m.backdrop_path = backgroundPrefix + m.backdrop_path;
                m.genres = "";
                m.isFile = false;
                if (!genreMap.isEmpty()) {
                    for (int i : m.genre_Ids) {
                        if (m.genres.equals("")) {
                            m.genres = m.genres + genreMap.get(i);
                        } else {
                            m.genres = m.genres + " " + genreMap.get(i);
                        }

                    }
                }

            }

            if (page == 1)
                movielist.clear();

            if (movieDatas != null) {
                movielist.addAll(movieDatas);

                movieAdapt.notifyDataSetChanged();
                loadedpage++;

            }


        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, "failure: " + error);

        }
    };

    Callback<MovieGenreCollection> responseGenre = new Callback<MovieGenreCollection>() {

        @Override
        public void success(MovieGenreCollection mg, Response response) {


            List<MovieGenre> movieGenre = mg.genres;

            for (MovieGenre m : movieGenre) {


                genreMap.put(m.id, m.name);
            }


        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, "failure: " + error);
        }
    };

    public MovieShowcaseFragment() {
        // Required empty public constructor
    }


    private void getFavorites() {
        movielist.clear();
        Cursor cr = null;
        try {
            cr = getActivity().getContentResolver().query(FavMovProvider.Movies.FAV_MOVIES,
                    null, null, null, null);
            if (cr != null) {
                int countc = cr.getColumnIndex(FavMovColumns.MOVIE_AUDIENCE);
                int namec = cr.getColumnIndex(FavMovColumns.MOVIE_NAME);
                int backc = cr.getColumnIndex(FavMovColumns.MOVIE_BACK_IMAGE);
                int genrec = cr.getColumnIndex(FavMovColumns.MOVIE_GENRE);
                int idc = cr.getColumnIndex(FavMovColumns.MOVIE_ID);
                int overviewc = cr.getColumnIndex(FavMovColumns.MOVIE_OVERVIEW);
                int posterc = cr.getColumnIndex(FavMovColumns.MOVIE_POSTER);
                int ratingc = cr.getColumnIndex(FavMovColumns.MOVIE_RATING);
                int releasec = cr.getColumnIndex(FavMovColumns.MOVIE_RELEASE_DATE);

                for (boolean hasItem = cr.moveToFirst(); hasItem; hasItem = cr.moveToNext()) {
                    MovieData md = new MovieData();
                    md.vote_count = cr.getInt(countc);
                    md.title = cr.getString(namec);
                    md.backdrop_path = cr.getString(backc);
                    md.genres = cr.getString(genrec);
                    md.id = cr.getInt(idc);
                    md.synopsis = cr.getString(overviewc);
                    md.posterURI = cr.getString(posterc);
                    md.rating = cr.getDouble(ratingc);
                    md.release_date = cr.getString(releasec);
                    md.isFile = true;

                    movielist.add(md);

                }

                movieAdapt.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.e(TAG, "exception: " + e);
        } finally {

            if (cr != null) {
                cr.close();
            }
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currCategory = CATEGORIES.values()[savedInstanceState.getInt(SORT_KIND)];

            movielist = savedInstanceState.getParcelableArrayList(MOVIE_LIST);
            scrollvalue = savedInstanceState.getInt(SCROLLTO);
        }
        setHasOptionsMenu(true);
        mGetMovieDataAdapter.getGenres(responseGenre);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(SORT_KIND, currCategory.ordinal());
        outState.putParcelableArrayList(MOVIE_LIST, movielist);
        outState.putBoolean(LOAD_STAT, loading);
        outState.putInt(FIRST_VIS, firstVisibleItem);
        outState.putInt(LOAD_PAGE, loadedpage);
        outState.putInt(VIS_COUNT, visibleItemCount);
        outState.putInt(PAGE, page);
        outState.putInt(TOT_COUNT, totalItemCount);
        outState.putInt(PREV_TOT, previousTotal);
        outState.putInt(SCROLLTO, scrollvalue);

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_showcase, container, false);
        RecyclerView gv = (RecyclerView) rootView.findViewById(R.id.movie_gridview);


        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        gv.setLayoutManager(mLayoutManager);
        gv.setHasFixedSize(true);

        movieAdapt = new MovieDataAdapter(getActivity(), movielist);
        gv.setAdapter(movieAdapt);

        gv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MovieData mv = movielist.get(position);

                        ((ShowcaseCallback) getActivity())
                                .onItemSelected(mv);
                    }
                })
        );

        gv.scrollTo(0, scrollvalue);
        gv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollvalue += dy;

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();


                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    page++;
                    if (currCategory == CATEGORIES.rating) {
                        updatemovies(getString(R.string.pref_rating));
                    } else if (currCategory == CATEGORIES.popular) {
                        updatemovies(getString(R.string.pref_popularity));
                    } else if (currCategory == CATEGORIES.fav) {
                        getFavorites();
                    }


                    // Do something

                    loading = true;
                }

            }


        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (currCategory == CATEGORIES.popular)
            updatemovies(getString(R.string.pref_popularity));
        else if (currCategory == CATEGORIES.rating)
            updatemovies(getString(R.string.pref_rating));
    }

    private void updatemovies(String criteria) {
        if (page != loadedpage) {
            try {
                mGetMovieDataAdapter.getMovieData(criteria, Integer.toString(page), response);
            } catch (Exception e) {
                Log.d("puttaloo", "Thread sleep error" + e);
            }

        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.showcase, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.popular) {


            page = 1;
            loadedpage = 0;
            currCategory = CATEGORIES.popular;
            updatemovies(getString(R.string.pref_popularity));

        } else if (item.getItemId() == R.id.rating) {

            currCategory = CATEGORIES.rating;
            page = 1;
            loadedpage = 0;
            updatemovies(getString(R.string.pref_rating));

        } else if (item.getItemId() == R.id.fav) {
            currCategory = CATEGORIES.fav;
            page = 1;
            loadedpage = 0;
            getFavorites();
        }
        return super.onOptionsItemSelected(item);
    }


}
