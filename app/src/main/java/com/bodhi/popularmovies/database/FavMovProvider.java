package com.bodhi.popularmovies.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;


/**
 * Created by gau on 6/23/2016.
 */
@ContentProvider(authority = FavMovProvider.AUTHORITY, database = FavMovDatabase.class,
        packageName = "com.bodhi.popularmovies.database.provider")
public final class FavMovProvider {
    public static final String AUTHORITY =
            "com.bodhi.popularmovies.database.FavMovProvider";


    interface Path {
        String MOVIES = "movies";
    }

    @TableEndpoint(table = FavMovDatabase.MOVIES)
    public static class Movies {
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/movie",
                defaultSort = FavMovColumns.MOVIE_RATING + " DESC")
        public static final Uri FAV_MOVIES = Uri.parse("content://" + AUTHORITY + "/movies");

        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.MOVIES + "/#",
                type = "vnd.android.cursor.item/movie",
                whereColumn = FavMovColumns.MOVIE_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/movies/" + id);
        }
    }


}