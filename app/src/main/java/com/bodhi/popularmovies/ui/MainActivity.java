package com.bodhi.popularmovies.ui;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bodhi.popularmovies.R;
import com.bodhi.popularmovies.data.MovieData;
import com.facebook.stetho.Stetho;

import java.io.File;

public class MainActivity extends AppCompatActivity implements
        MovieShowcaseFragment.ShowcaseCallback {

    private boolean mTwoPane;
    private final String MOVIE_DETAILFRAGMENT_TAG = "MDFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment(),
                                MOVIE_DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {


            mTwoPane = false;
        }
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        File imagesFolder = new File(Environment.getExternalStorageDirectory(), getString(R.string.image_folder));
        imagesFolder.mkdirs();

    }

    @Override
    public void onItemSelected(MovieData md) {
        if (mTwoPane) {

            MovieDetailFragment.setMovieData(md);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, new MovieDetailFragment(),
                            MOVIE_DETAILFRAGMENT_TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this,
                    MovieDetailsActivity.class);

            intent.putExtra("com.bodhi.popularmovies.data.MovieData", md);
            startActivity(intent);
        }

    }
}
