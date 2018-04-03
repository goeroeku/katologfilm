package ic.aiczone.mymovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static ic.aiczone.mymovies.entity.DatabaseContract.TABLE_NAME;

/**
 * Created by aic on 02/04/18.
 * Email goeroeku@gmail.com.
 */

public class FavoriteHelper {

    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor getsProvider() {
        return database.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor getByIdProvider(String id) {
        return database.query(
                TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long addProvider(ContentValues values) {
        return database.insert(
                TABLE_NAME,
                null,
                values
        );
    }

    public int editProvider(String id, ContentValues values) {
        return database.update(
                TABLE_NAME,
                values,
                _ID + " = ?",
                new String[]{id}
        );
    }

    public int delProvider(String id) {
        return database.delete(
                TABLE_NAME,
                _ID + " = ?",
                new String[]{id}
        );
    }
}
