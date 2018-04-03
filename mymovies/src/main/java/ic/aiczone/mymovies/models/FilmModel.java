package ic.aiczone.mymovies.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import ic.aiczone.mymovies.utils.Tools;

import static android.provider.BaseColumns._ID;
import static ic.aiczone.mymovies.entity.DatabaseContract.FavoriteColumns.OVERVIEW;
import static ic.aiczone.mymovies.entity.DatabaseContract.FavoriteColumns.POPULARITY;
import static ic.aiczone.mymovies.entity.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static ic.aiczone.mymovies.entity.DatabaseContract.FavoriteColumns.RELEASE;
import static ic.aiczone.mymovies.entity.DatabaseContract.FavoriteColumns.TITLE;
import static ic.aiczone.mymovies.entity.DatabaseContract.getColumnInt;
import static ic.aiczone.mymovies.entity.DatabaseContract.getColumnString;

public class FilmModel implements Parcelable {
    private int id;
    private String title, release, overview, popularity, backdropPath, posterPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public FilmModel() {
    }

    public FilmModel(Cursor cursor) {
        Log.d("cek", String.valueOf(cursor.getInt(0)));
        this.id = getColumnInt(cursor, _ID);
        this.posterPath = getColumnString(cursor, POSTER_PATH);
        this.title = getColumnString(cursor, TITLE);
        this.release = getColumnString(cursor, RELEASE);
        this.overview = getColumnString(cursor, OVERVIEW);
        this.popularity = getColumnString(cursor, POPULARITY);
    }

    public FilmModel(JSONObject object) {

        try {
            this.id = object.getInt("id");
            this.posterPath = object.getString("poster_path");
            this.title = object.getString("title");
            this.release = Tools.getLongFormat(object.getString("release_date"));
            String desc = object.getString("overview");
            this.overview = desc;
            this.popularity = object.getString("popularity");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.overview);
        dest.writeString(this.popularity);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
    }

    protected FilmModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.popularity = in.readString();
        this.backdropPath = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<FilmModel> CREATOR = new Creator<FilmModel>() {
        @Override
        public FilmModel createFromParcel(Parcel source) {
            return new FilmModel(source);
        }

        @Override
        public FilmModel[] newArray(int size) {
            return new FilmModel[size];
        }
    };
}
