package kr.mintech.sleep.tight.activities.Local_db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import kr.mintech.sleep.tight.activities.Local_db.dbHelper_local;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class backgroundTask {

    dbHelper_local mdb;


    public backgroundTask(Context cont) {
        mdb = new dbHelper_local(cont);
    }

    public void createUser_DB() {

        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelper_local.USERS_COLUMN_ID, id);
        values.put(dbHelper_local.USERS_COLUMN_PASS, passwd);

        db.insert(dbHelper_local.TABLE_USERS, null, values);
        db.close();
    }
}
