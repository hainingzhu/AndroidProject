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
        Log.w("hjx", "create background service");
        //mdb = new dbHelper_local(getContext());
    }

    @Override
    protected void onHandleIntent(Intent dbint) {
        String data = dbint.getDataString();
        Uri u = Uri.parse(data);
        String action = u.getPath();

        Log.w("hjxx", u.getPath());
        Log.w("hjxxxx", u.getQuery());

        if (action.equals("/addusers"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.USERS_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.USERS_COLUMN_NAME, u.getQueryParameter("nickname"));
            values.put(dbHelper_local.USERS_COLUMN_BIRTHYEAR, u.getQueryParameter("birthYear"));
            values.put(dbHelper_local.USERS_COLUMN_GENDER, u.getQueryParameter("gender"));
            values.put(dbHelper_local.USERS_COLUMN_SLEEPCONDITION, u.getQueryParameter("sleepCondition"));
            //values.put(dbHelper_local.USERS_COLUMN_START_DATE, getDate("start_date") );

            db.insert(dbHelper_local.TABLE_USERS, null, values);
            db.close();

        }
        else if (action.equals("/addactivities"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.ACTIVITIES_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.ACTIVITIES_COLUMN_USERID, u.getQueryParameter("user_id"));
            //values.put(dbHelper_local.ACTIVITIES_COLUMN_COLOR, u.getQueryParameter("color"));
            //values.put(dbHelper_local.ACTIVITIES_COLUMN_POSITION, u.getQueryParameter("position") );
            values.put(dbHelper_local.ACTIVITIES_COLUMN_ACTIVITY_NAME, u.getQueryParameter("activity_name"));
            //values.put(dbHelper_local.ACTIVITIES_COLUMN_DEFAULTTYPE, u.getQueryParameter("activity_name"));

            db.insert(dbHelper_local.TABLE_ACTIVITIES, null, values);
            db.close();

        }
        else if (action.equals("/addsleeptracks"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_USERID, u.getQueryParameter("user_id"));
            //values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_DIARY_DATE, u.getdate("diary_date"));
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_INBED_TIME, u.getQueryParameter("in_bed_time") );
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SLEEP_TIME, u.getQueryParameter("sleep_time"));
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_WAKEUP_TIME, u.getQueryParameter("wake_up_time"));

            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_OUTBED_TIME, u.getQueryParameter("out_bed_time"));
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SLEEP_QUALITY, u.getQueryParameter("sleep_quality"));
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_AWAKE_COUNT, u.getQueryParameter("awake_count"));
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_TOTAL_AWAKE_TIME, u.getQueryParameter("total_awake_time") );
            values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SLEEP_LATENCY, u.getQueryParameter("sleep_latency"));
            //values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_CREATE_TIME, u.gettime("create_time"));

            db.insert(dbHelper_local.TABLE_SLEEP_TRACKS, null, values);
            db.close();

        }
        else if (action.equals("/addactivity_tracks"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ACTIVITYID, u.getQueryParameter("activity_id"));
            values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_USERID, u.getQueryParameter("user_id"));
            values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_RECORDTYPE, u.getQueryParameter("record_type") );
            values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_STARTTIME, u.getQueryParameter("start_time"));
            values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ENDTIME, u.getQueryParameter("end_time"));
            //values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_CREATETIME, u.gettime("create_time"));

            db.insert(dbHelper_local.TABLE_ACTIVITY_TRACKS, null, values);
            db.close();

        }
        else if (action.equals("/addsleep_disturbances"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.SLEEP_DISTURBANCES_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.SLEEP_DISTURBANCES_COLUMN_DISTURBANCENAME, u.getQueryParameter("disturbance_name"));
            values.put(dbHelper_local.SLEEP_DISTURBANCES_COLUMN_USERID, u.getQueryParameter("user_id"));

            db.insert(dbHelper_local.TABLE_SLEEP_DISTURBANCES, null, values);
            db.close();

        }
        else if (action.equals("/addsleep_rituals"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_RITUALNAME, u.getQueryParameter("ritual_name"));
            values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_USERID, u.getQueryParameter("user_id"));
            values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_FREQUENCY, u.getQueryParameter("frequency"));


            db.insert(dbHelper_local.TABLE_SLEEP_RITUALS, null, values);
            db.close();

        }
        else if (action.equals("/addsleep_track_disturbances"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.SLEEP_TRACK_DISTURBANCES_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.SLEEP_TRACK_DISTURBANCES_COLUMN_DISTURBANCEID, u.getQueryParameter("sleep_disturbance_id"));
            values.put(dbHelper_local.SLEEP_TRACK_DISTURBANCES_COLUMN_SLEEP_TRACKID, u.getQueryParameter("sleep_track_id"));

            db.insert(dbHelper_local.TABLE_SLEEP_TRACK_DISTURBANCES, null, values);
            db.close();

        }
        else if (action.equals("/addsleep_track_rituals"))
        {
            SQLiteDatabase db = mdb.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(dbHelper_local.SLEEP_TRACK_RITUALS_COLUMN_ID, u.getQueryParameter("id"));
            values.put(dbHelper_local.SLEEP_TRACK_RITUALS_COLUMN_RITUAL_ID, u.getQueryParameter("sleep_ritual_id"));
            values.put(dbHelper_local.SLEEP_TRACK_RITUALS_COLUMN_SLEEP_TRACKID, u.getQueryParameter("sleep_track_id"));

            db.insert(dbHelper_local.TABLE_SLEEP_TRACK_RITUALS, null, values);
            db.close();

        }
    }



    /*
    public void createUser_DB() {


    }
    */
}
