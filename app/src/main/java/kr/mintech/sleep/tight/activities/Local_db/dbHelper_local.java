package kr.mintech.sleep.tight.activities.Local_db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by hbz5037 on 2/19/16.
 */
public class dbHelper_local extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB_Local.db";
    public static final String TABLE_USERS = "user";
    public static final String USERS_COLUMN_ID = "userid";
    public static final String USERS_COLUMN_PASS = "pwd";

    public static final String TABLE_SLEEP_TRACKS = "sleep_tracks";
    public static final String ST_COLUMN;


    private static final String TABLE_CREATE = "Create Table Contacts (userid text primary key not null, pwd text not null);";


    public dbHelper_local (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE);

        db.execSQL(TABLE_CREATE);

        db.execSQL(TABLE_CREATE);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
