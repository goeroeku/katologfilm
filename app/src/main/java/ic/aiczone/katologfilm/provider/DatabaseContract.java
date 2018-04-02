package ic.aiczone.katologfilm.provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by aic on 02/04/18.
 * Email goeroeku@gmail.com.
 */

public class DatabaseContract {
    public static String TABLE_NAME = "tb_favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String RELEASE = "release";
        public static String OVERVIEW = "overview";
        public static String POPULARITY = "popularity";
        public static String POSTER_PATH = "posterPath";
        public static String BACKDROP_PATH = "backdropPath";
    }

    public static final String AUTHORITY = "ic.aiczone.katologfilm";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
