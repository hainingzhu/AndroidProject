package kr.mintech.sleep.tight.activities.Local_db;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import kr.mintech.sleep.tight.activities.Local_db.dbHelper_local;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class backgroundTask extends IntentService {


    dbHelper_local mdb;

    public backgroundTask() {
        super("db local helper");
        Log.w("hongjian", "create background service");
        //mdb = new dbHelper_local(getContext());
    }

    @Override
    protected void onHandleIntent(Intent dbint) {
        String data = dbint.getDataString();
        Uri u = Uri.parse(data);
        String action = u.getPath();

        Log.w("hongjian", u.getPath());
        Log.w("hongjian", u.getQuery());

        if (action.equals("/addusers"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(dbHelper_local.USERS_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.USERS_COLUMN_NAME, u.getQueryParameter("nickname"));
            values.put(dbHelper_local.USERS_COLUMN_GENDER, u.getQueryParameter("gender"));
            //values.put(dbHelper_local.USERS_COLUMN_START_DATE, getCurrentTime() );

            db.insert(dbHelper_local.TABLE_USERS, null, values);
            db.close();
        } else if (action.equals("/addDiaryTracks")) {

        } else if (action.equals("/blablazhu")) {

        }
    }



    /*
    public void createUser_DB() {


    }
    */
}
