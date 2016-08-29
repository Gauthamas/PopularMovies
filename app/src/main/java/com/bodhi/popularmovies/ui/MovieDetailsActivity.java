package com.bodhi.popularmovies.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.bodhi.popularmovies.data.MovieData;
import com.bodhi.popularmovies.R;


public class MovieDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Get the data from bundle
        Bundle b = getIntent().getExtras();
        MovieData mv = b.getParcelable("com.bodhi.popularmovies.data.MovieData");
        MovieDetailFragment.setMovieData(mv);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, new MovieDetailFragment())
                    .commit();
        }


    }


}
