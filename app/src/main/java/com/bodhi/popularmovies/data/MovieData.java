package com.bodhi.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gau on 4/24/2016.
 */
public class MovieData implements Parcelable {
    @SerializedName("poster_path")
    public String posterURI;

    @SerializedName("overview")
    public String synopsis;

    @SerializedName("vote_average")
    public Double rating;

    @SerializedName("title")
    public String title;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("id")
    public Integer id;

    @SerializedName("backdrop_path")
    public String backdrop_path;

    @SerializedName("vote_count")
    public Integer vote_count;

    @SerializedName("genre_ids")
    public List<Integer> genre_Ids = new ArrayList<Integer>();

    public String genres;

    public boolean isFile;


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterURI);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeDouble(rating);
        dest.writeString(release_date);
        dest.writeInt(id);
        dest.writeString(backdrop_path);
        dest.writeString(genres);
        dest.writeInt(vote_count);

    }

    private MovieData(Parcel in) {
        posterURI = in.readString();
        title = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        release_date = in.readString();
        id = in.readInt();
        backdrop_path = in.readString();
        genres = in.readString();
        vote_count = in.readInt();

    }

    public static final Parcelable.Creator<MovieData> CREATOR
            = new Parcelable.Creator<MovieData>() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public MovieData(String URI, String tit, String synops, Double rat,
                     String release, Integer id) {
        this.posterURI = URI;
        this.title = tit;
        this.synopsis = synops;
        this.rating = rat;
        this.release_date = release_date;
        this.id = id;

    }

    public MovieData() {

    }

}
