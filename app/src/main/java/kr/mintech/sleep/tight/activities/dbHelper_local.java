package kr.mintech.sleep.tight.activities;

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
    private static final String TABLE_NAME = "userDB_Local";
    private static final String COLUMN_ID = "userid";
    private static final String COLUMN_PASS = "pwd";

    SQLiteDatabase db;
    private static final String TABLE_CREATE = "Create Table Contacts (userid text primary key not null, pwd text not null);";


    public dbHelper_local (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertColumn(userDB_Local c)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, c.getId());
        values.put(COLUMN_PASS,c.getPwd());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean findExistId(String str)
    {
        String uid = "";
        db = this.getWritableDatabase();
        boolean find = false;
        String query = "select userid, pwd from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            do
            {
                uid = cursor.getString(0);
                if(uid.equals(str))
                {
                    find = true;
                    break;
                }
            }
            while(cursor.moveToNext());
        }


        return find;
    }

    public String searchpass(String str)
    {
        String uid = "";
        String password= "Not found!";
        db = this.getWritableDatabase();
        String query = "select userid, pwd from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do
            {
                uid = cursor.getString(0);
                if(uid.equals(str))
                {
                    password = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());

        }

        db.close();
        return password;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
