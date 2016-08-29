package com.bodhi.popularmovies.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by gau on 6/23/2016.
 */
@Database(version = FavMovDatabase.VERSION, packageName = "com.bodhi.popularmovies.database.provider")
public class FavMovDatabase {

    public static final int VERSION = 1;
    @Table(FavMovColumns.class)
    public static final String MOVIES = "movies";
}
