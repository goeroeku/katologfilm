package ic.aiczone.katologfilm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aic on 26/02/18.
 */

public class Tools {
    //String[] hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Minggu"};
    public static String getFormatTime(String time) {
        String sTime = time;
        if (time.length() == 10) {
            String[] aTime = time.split("-");
            sTime = aTime[2] + "-" + aTime[1] + "-" + aTime[0];
        }
        return sTime;
    }

    public static String getLongFormat(String time) {
        SimpleDateFormat form_src = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tTime = form_src.parse(time);
            SimpleDateFormat form_dst = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            return form_dst.format(tTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }

    }

    public static boolean currentDate(String date) {
        SimpleDateFormat form_src = new SimpleDateFormat("yyyy-MM-dd");
        Date mDate = null;
        Date mCoba = null;
        Date mCoba1 = null;
        try {
            mDate = form_src.parse(date);
            mCoba = form_src.parse("2017-10-27");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mCoba.equals(mDate) || mCoba1.equals(mDate)) {
        /*if (mCoba.equals(mDate)) {*/
            return true;
        } else {
            return false;
        }
    }
}
