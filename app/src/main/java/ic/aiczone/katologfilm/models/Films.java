package ic.aiczone.katologfilm.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ic.aiczone.katologfilm.utils.Tools;

/**
 * Created by aic on 26/02/18.
 */

public class Films implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String time;
    private String linkImage;

    public Films() {}

    public Films(JSONObject object) {

        try {
            this.id = object.getInt("id");
            this.title = object.getString("title");
            String desc = object.getString("overview");
            this.description = desc;
            this.time = Tools.getLongFormat(object.getString("release_date"));
            this.linkImage = object.getString("poster_path");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.time);
        dest.writeString(this.linkImage);
    }

    protected Films(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.time = in.readString();
        this.linkImage = in.readString();
    }

    public static final Parcelable.Creator<Films> CREATOR = new Parcelable.Creator<Films>() {
        @Override
        public Films createFromParcel(Parcel source) {
            return new Films(source);
        }

        @Override
        public Films[] newArray(int size) {
            return new Films[size];
        }
    };
}
