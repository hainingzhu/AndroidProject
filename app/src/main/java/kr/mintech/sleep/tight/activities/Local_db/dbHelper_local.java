package kr.mintech.sleep.tight.activities.Local_db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.mintech.sleep.tight.utils.DateTime;

/**
 * Created by hbz5037 on 2/19/16.
 */
public class dbHelper_local extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB_Local.db";

    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "nickname";
    public static final String USERS_COLUMN_BIRTHYEAR = "birthYear";
    public static final String USERS_COLUMN_GENDER = "gender";
    public static final String USERS_COLUMN_SLEEPCONDITION = "sleepCondition";
    public static final String USERS_COLUMN_START_DATE = "start_date";


    public static final String TABLE_SLEEP_TRACKS = "sleep_tracks";
    public static final String SLEEP_TRACKS_COLUMN_ID = "id";
    public static final String SLEEP_TRACKS_COLUMN_USERID = "user_id";
    public static final String SLEEP_TRACKS_COLUMN_DIARY_DATE = "diary_date";
    public static final String SLEEP_TRACKS_COLUMN_INBED_TIME = "in_bed_time";
    //public static final String SLEEP_TRACKS_COLUMN_SLEEP_TIME = "sleep_time";
    public static final String SLEEP_TRACKS_COLUMN_WAKEUP_TIME = "wake_up_time";
    public static final String SLEEP_TRACKS_COLUMN_OUTBED_TIME = "out_bed_time";
    public static final String SLEEP_TRACKS_COLUMN_SLEEP_QUALITY = "sleep_quality";
    public static final String SLEEP_TRACKS_COLUMN_AWAKE_COUNT = "awake_count";
    public static final String SLEEP_TRACKS_COLUMN_TOTAL_AWAKE_TIME = "total_awake_time";
    public static final String SLEEP_TRACKS_COLUMN_SLEEP_LATENCY = "sleep_latency";
    public static final String SLEEP_TRACKS_COLUMN_CREATE_TIME = "create_time";
    public static final String SLEEP_TRACKS_COLUMN_SDURATION = "sleepDuration";




    public static final String TABLE_ACTIVITY_TRACKS = "activity_tracks";
    public static final String ACTIVITY_TRACKS_COLUMN_ID = "id";
    public static final String ACTIVITY_TRACKS_COLUMN_ACTIVITYID = "activity_id";
    public static final String ACTIVITY_TRACKS_COLUMN_USERID = "user_id";
    public static final String ACTIVITY_TRACKS_COLUMN_RECORDTYPE = "record_type";
    public static final String ACTIVITY_TRACKS_COLUMN_STARTTIME = "actionStartedAt";
    public static final String ACTIVITY_TRACKS_COLUMN_ENDTIME = "actionEndedAt";
    public static final String ACTIVITY_TRACKS_COLUMN_CREATETIME = "create_time";
    public static final String ACTIVITY_TRACKS_COLUMN_ACTIVITYNAME = "activityName";
    public static final String ACTIVITY_TRACKS_COLUMN_TRACKTYPE = "trackType";
    public static final String ACTIVITY_TRACKS_COLUMN_SORTPOSITION = "sortPosition";
    public static final String ACTIVITY_TRACKS_COLUMN_COLOR = "color";




    public static final String TABLE_ACTIVITIES = "activities";
    public static final String ACTIVITIES_COLUMN_ID = "id";
                                                                                 //public static final String ACTIVITY_COLUMN_ACTIVITYID = "activityId";
    public static final String ACTIVITIES_COLUMN_USERID = "user_id";
    //public static final String ACTIVITIES_COLUMN_COLOR = "color";
    //public static final String ACTIVITIES_COLUMN_POSITION = "position";
    public static final String ACTIVITIES_COLUMN_ACTIVITY_NAME = "activity_name";
    //public static final String ACTIVITIES_COLUMN_DEFAULTTYPE = "defaultType";
    public static final String ACTIVITIES_COLUMN_ISHIDE = "isHide";




    public static final String TABLE_SLEEP_DISTURBANCES = "sleep_disturbances";
    public static final String SLEEP_DISTURBANCES_COLUMN_ID = "id";
    public static final String SLEEP_DISTURBANCES_COLUMN_DISTURBANCENAME = "disturbance_name";
    public static final String SLEEP_DISTURBANCES_COLUMN_USERID = "user_id";



    public static final String TABLE_SLEEP_RITUALS = "sleep_rituals";
    public static final String SLEEP_RITUALS_COLUMN_ID = "id";
    public static final String SLEEP_RITUALS_COLUMN_RITUALNAME = "ritual_name";
    public static final String SLEEP_RITUALS_COLUMN_USERID = "user_id";
    public static final String SLEEP_RITUALS_COLUMN_FREQUENCY = "frequency";



    public static final String TABLE_SLEEP_TRACK_DISTURBANCES = "sleep_track_disturbances";
    public static final String SLEEP_TRACK_DISTURBANCES_COLUMN_ID = "id";
    public static final String SLEEP_TRACK_DISTURBANCES_COLUMN_DISTURBANCEID = "sleep_disturbance_id";
    public static final String SLEEP_TRACK_DISTURBANCES_COLUMN_SLEEP_TRACKID = "sleep_track_id";



    public static final String TABLE_SLEEP_TRACK_RITUALS = "sleep_track_rituals";
    public static final String SLEEP_TRACK_RITUALS_COLUMN_ID = "id";
    public static final String SLEEP_TRACK_RITUALS_COLUMN_RITUAL_ID = "sleep_ritual_id";
    public static final String SLEEP_TRACK_RITUALS_COLUMN_SLEEP_TRACKID = "sleep_track_id";




    private static final String TABLE_CREATE_USERS = "Create Table users (id integer, " +
            "nickname text not null, birthYear INTEGER, gender text, sleepCondition text, start_date text);";

    private static final String TABLE_CREATE_SLEEP_TRACKS = "Create Table sleep_tracks (id INTEGER primary key AUTOINCREMENT, " +
            "user_id INTEGER REFERENCES users (id), diary_date DATE, in_bed_time TIME[(P)] [WITHOUT TIME ZONE], " +
            "wake_up_time TIME[(P)] [WITHOUT TIME ZONE], " +
            "out_bed_time TIME[(P)] [WITHOUT TIME ZONE], sleep_quality NUMERIC(5, 5), awake_count INT, " +
            "total_awake_time integer, sleep_latency integer," +
            "create_time TIME[(P)] [WITHOUT TIME ZONE], sleepDuration integer);";


    private static final String TABLE_CREATE_ACTIVITIES = "Create Table activities (id int PRIMARY KEY," +
            " user_id INTEGER REFERENCES users (id), activity_name TEXT, isHide INTEGER);";


    private static final String TABLE_CREATE_ACTIVITY_TRACKS = "Create Table activity_tracks (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "activity_id INTEGER REFERENCES activities (id),user_id INTEGER REFERENCES users (id)," +
            "record_type TEXT, actionStartedAt TEXT, actionEndedAt TEXT, create_time TEXT," +
            "activityName TEXT, trackType TEXT, sortPosition INTEGER, color TEXT);";





    private static final String TABLE_CREATE_SLEEP_DISTURBANCES = "Create Table sleep_disturbances (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "disturbance_name TEXT, user_id INTEGER REFERENCES users (id));";

    private static final String TABLE_CREATE_SLEEP_RITUALS = "Create Table sleep_rituals (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ritual_name TEXT, user_id INTEGER REFERENCES users (id), frequency INTEGER );";

    private static final String TABLE_CREATE_SLEEP_TRACK_DISTURBANCES = "Create Table sleep_track_disturbances (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "sleep_disturbance_id INTEGER REFERENCES sleep_disturbance (id), sleep_track_id INTEGER REFERENCES sleep_tracks (id));";

    private static final String TABLE_CREATE_SLEEP_TRACK_RITUALS = "Create Table sleep_track_rituals (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "sleep_ritual_id INTEGER REFERENCES sleep_rituals (id), sleep_track_id INTEGER REFERENCES sleep_tracks (id));";







    public dbHelper_local (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_SLEEP_TRACKS);
        db.execSQL(TABLE_CREATE_ACTIVITIES);
        db.execSQL(TABLE_CREATE_ACTIVITY_TRACKS);
        db.execSQL(TABLE_CREATE_SLEEP_DISTURBANCES);
        db.execSQL(TABLE_CREATE_SLEEP_RITUALS );
        db.execSQL(TABLE_CREATE_SLEEP_TRACK_DISTURBANCES);
        db.execSQL(TABLE_CREATE_SLEEP_TRACK_RITUALS);

        //this.db = db;


    }


    public static void insertUser(Users u, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.USERS_COLUMN_ID, u.id);
        values.put(dbHelper_local.USERS_COLUMN_NAME, u.loginName);
        values.put(dbHelper_local.USERS_COLUMN_BIRTHYEAR, u.birthYear);
        values.put(dbHelper_local.USERS_COLUMN_GENDER, u.gender);
        values.put(dbHelper_local.USERS_COLUMN_SLEEPCONDITION, u.sleepCondition);
        values.put(dbHelper_local.USERS_COLUMN_START_DATE, u.start_date);

        db.insert(dbHelper_local.TABLE_USERS, null, values);
        db.close();
    }

    public static int curUserID(Context cont)
    {
        Log.w("WHJ", "function call: curUserID()");
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getReadableDatabase();

        Cursor res = db.query(TABLE_USERS, new String[]{USERS_COLUMN_ID}, null, null, null, null, null, "1");
        int cidx = res.getColumnIndex(USERS_COLUMN_ID);
        //Log.w("WHJ", String.format("USERS_COLUMN_ID column index %d", cidx));
        int uid = -100;
        if (res.moveToFirst()) {
            uid = res.getInt(cidx);
            Log.w("WHJ", String.format("User %d is in local DB", uid));
        }
        res.close();
        db.close();
        return uid;
    }


    public static void insertActivities(activities a, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.ACTIVITIES_COLUMN_ID, a.id);
        values.put(dbHelper_local.ACTIVITIES_COLUMN_USERID, a.user_id);
        values.put(dbHelper_local.ACTIVITIES_COLUMN_ACTIVITY_NAME, a.activity_name);
        values.put(dbHelper_local.ACTIVITIES_COLUMN_ISHIDE, a.isHide);

        db.insert(dbHelper_local.TABLE_ACTIVITIES, null, values);
        db.close();

    }

    public static boolean findActivities(String activity, Context con)
    {
        Log.w("WHJ", "function call: findActivities()");
        dbHelper_local mdb = new dbHelper_local(con);
        SQLiteDatabase db = mdb.getReadableDatabase();

        Cursor res = db.query(TABLE_ACTIVITIES, new String[]{ACTIVITIES_COLUMN_ID}, String.format("%s=\"%s\"", ACTIVITIES_COLUMN_ACTIVITY_NAME, activity), null, null, null, null, null);
        if (res.moveToFirst()) {
            res.close();
            db.close();
            return true;
        } else {
            res.close();
            db.close();
            return false;
        }
    }




    public static void insertActivityTracks (activity_tracks aT, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ID, aT.id);
        values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ACTIVITYID, aT.activity_id);
        values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_USERID, aT.user_id);
        //values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_RECORDTYPE, aT.record_type );
        values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_STARTTIME, aT.actionStartedAt);
        values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ENDTIME, aT.actionEndedAt);
        values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_CREATETIME, aT.create_time);

        values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_ACTIVITYNAME, aT.activityName);
       // values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_TRACKTYPE, aT.trackType);
       // values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_SORTPOSITION, aT.sortPosition);
       // values.put(dbHelper_local.ACTIVITY_TRACKS_COLUMN_COLOR, aT.color);

        db.insert(dbHelper_local.TABLE_ACTIVITY_TRACKS, null, values);
        db.close();

    }



    public static void insertSleepDisturbances (sleep_disturbances sd, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.SLEEP_DISTURBANCES_COLUMN_DISTURBANCENAME, sd.disturbance_name);
        values.put(dbHelper_local.SLEEP_DISTURBANCES_COLUMN_USERID, sd.user_id);

        db.insert(dbHelper_local.TABLE_SLEEP_DISTURBANCES, null, values);
        db.close();
    }



    public static void insertSleepRituals (sleep_rituals sr, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_RITUALNAME, sr.ritual_name);
        values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_USERID, sr.user_id);
        values.put(dbHelper_local.SLEEP_RITUALS_COLUMN_FREQUENCY, sr.frequency);

        db.insert(dbHelper_local.TABLE_SLEEP_RITUALS, null, values);
        db.close();
    }




    public static void insertSleepTrackRituals (sleep_track_rituals str, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.SLEEP_TRACK_RITUALS_COLUMN_ID, str.id);
        values.put(dbHelper_local.SLEEP_TRACK_RITUALS_COLUMN_RITUAL_ID, str.sleep_ritual_id);
        values.put(dbHelper_local.SLEEP_TRACK_RITUALS_COLUMN_SLEEP_TRACKID, str.sleep_track_id);

        db.insert(dbHelper_local.TABLE_SLEEP_TRACK_RITUALS, null, values);
        db.close();
    }




    public static void insertSleepTrackDisturbance (sleep_track_disturbances std, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.SLEEP_TRACK_DISTURBANCES_COLUMN_ID, std.id);
        values.put(dbHelper_local.SLEEP_TRACK_DISTURBANCES_COLUMN_DISTURBANCEID, std.sleep_disturbance_id);
        values.put(dbHelper_local.SLEEP_TRACK_DISTURBANCES_COLUMN_SLEEP_TRACKID, std.sleep_track_id);

                db.insert(dbHelper_local.TABLE_SLEEP_TRACK_DISTURBANCES, null, values);
        db.close();
    }



    public static void insertSleep_tracks (sleep_tracks st, Context cont)
    {
        dbHelper_local mdb = new dbHelper_local(cont);
        SQLiteDatabase db = mdb.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_USERID, st.user_id);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_DIARY_DATE, st.diary_date);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_INBED_TIME, st.in_bed_time );
        //values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SLEEP_TIME, st.sleep_time);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_WAKEUP_TIME, st.wake_up_time);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_OUTBED_TIME, st.out_bed_time);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SLEEP_QUALITY, st.sleep_quality);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_AWAKE_COUNT, st.awake_count);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_TOTAL_AWAKE_TIME, st.total_awake_time );
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SLEEP_LATENCY, st.sleep_latency);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_CREATE_TIME, st.create_time);
        values.put(dbHelper_local.SLEEP_TRACKS_COLUMN_SDURATION, st.sleepDuration);

        db.insert(dbHelper_local.TABLE_SLEEP_TRACKS, null, values);
        db.close();
    }






    public static void insertInto_ActivityDB(Context con, int actionID, String actName, String startT)
    {
        Log.w("WHJ", String.format("insert activity %s with ID %d", actName, actionID));
        int uid = dbHelper_local.curUserID(con);
        activities a = new activities(uid, actionID, actName, 0);

        if (dbHelper_local.findActivities(a.activity_name, con)) {
            Log.w("WHJ", String.format("Activity %s is already in the database", a.activity_name));
        } else {
            Log.w("WHJ", String.format("User %d with activity %s is inserted", uid, a.activity_name));
            dbHelper_local.insertActivities(a, con);
        }
    }

    /**
     * insert data into local database, Table activity tract
     *
     * Input:
     *
     * @param actionID
     * @param actName
     *
     * Table:
     * id - autoincrement
     * activity_id - actionID
     * user_id - in the function. "dbHelper_local.curUserID(this._context)"
     * record_type - None
     * actionStartedAt - startT
     * actionEndedAt - None
     * createTime - new SimpleDateFormat("dd-MM-yyyy").format(new Date());
     * activityName - actName
     * tractType -
     *
     */

    public static void insertInto_ActivityTrackDB(Context con, int actionID, String actionStartedAt, String actionEndedAt, String actName)
    {
        int uid = dbHelper_local.curUserID(con);
        String create_time = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        activity_tracks at = new activity_tracks(actionID, uid, actionStartedAt, actionEndedAt, create_time, actName);


        Log.w("zzz", String.format("User %d with activity id %d activity name %s is inserted, action start at %s end at %s creat time is %s."
                , uid, actionID, at.activityName, at.actionStartedAt, at.actionEndedAt, at.create_time));
        dbHelper_local.insertActivityTracks(at, con);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query_users = "DROP TABLE IF EXISTS"+TABLE_USERS;
        String query_sleep_tracks = "DROP TABLE IF EXISTS"+TABLE_SLEEP_TRACKS;
        String query_activities = "DROP TABLE IF EXISTS"+TABLE_ACTIVITIES;
        String query_activity_tracks = "DROP TABLE IF EXISTS"+TABLE_ACTIVITY_TRACKS;

        String query_sleep_disturbance = "DROP TABLE IF EXISTS"+TABLE_SLEEP_DISTURBANCES;
        String query_sleep_rituals = "DROP TABLE IF EXISTS"+TABLE_SLEEP_RITUALS;
        String query_sleep_track_disturbances = "DROP TABLE IF EXISTS"+TABLE_SLEEP_TRACK_DISTURBANCES;
        String query_sleep_track_rituals = "DROP TABLE IF EXISTS"+TABLE_SLEEP_TRACK_RITUALS;


        db.execSQL(query_users);
        db.execSQL(query_sleep_tracks);
        db.execSQL(query_activities);
        db.execSQL(query_activity_tracks);
        db.execSQL(query_sleep_disturbance);
        db.execSQL(query_sleep_rituals);
        db.execSQL(query_sleep_track_disturbances);
        db.execSQL(query_sleep_track_rituals);

        this.onCreate(db);
    }
}
