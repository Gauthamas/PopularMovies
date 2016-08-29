package com.bodhi.popularmovies.database;


import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by sam_chordas on 8/11/15.
 */

public interface FavMovColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID =
            "_id";
    @DataType(DataType.Type.INTEGER)
    public static final String MOVIE_ID =
            "m_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MOVIE_NAME = "m_name";

    @DataType(DataType.Type.TEXT)
    public static final String MOVIE_POSTER = "m_poster";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MOVIE_OVERVIEW = "m_overview";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MOVIE_RELEASE_DATE = "m_release_date";

    @DataType(DataType.Type.TEXT)
    public static final String MOVIE_BACK_IMAGE = "m_back_image";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String MOVIE_GENRE = "m_genre";

    @DataType(DataType.Type.INTEGER)
    public static final String MOVIE_AUDIENCE = "m_audience";

    @DataType(DataType.Type.REAL)
    public static final String MOVIE_RATING = "m_rating";


}
