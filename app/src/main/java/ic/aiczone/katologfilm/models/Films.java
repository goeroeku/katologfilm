package ic.aiczone.katologfilm.models;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ic.aiczone.katologfilm.utils.Tools;

/**
 * Created by aic on 26/02/18.
 */

public class Films {

    private int id;
    private String title;
    private String description;
    private String time;
    private String linkImage;

    public Films(JSONObject object) {

        try {
            this.id = object.getInt("id");
            this.title = object.getString("title");
            String desc = object.getString("overview");
            /*if(desc.length() > 100){
                desc = desc.substring(0, 100) + "...";
            }else if(desc.length() < 1){
                desc = "";
            }*/
            this.description = desc;
            //this.time = Tools.getFormatTime(object.getString("release_date"));
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
}
