package ic.aiczone.katologfilm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.OVERVIEW;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.POPULARITY;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.RELEASE;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.TITLE;
import static ic.aiczone.katologfilm.provider.DatabaseContract.TABLE_NAME;

/**
 * Created by aic on 02/04/18.
 * Email goeroeku@gmail.com.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "catalogue_movie";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = String.format("CREATE TABLE %s"
                        + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT NOT NULL)",
                TABLE_NAME,
                _ID,
                POSTER_PATH,
                TITLE,
                RELEASE,
                OVERVIEW,
                POPULARITY
        );

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
