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

    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "nickname";
    public static final String USERS_COLUMN_PASS = "passwd";
    public static final String USERS_COLUMN_GENDER = "gender";
    public static final String USERS_COLUMN_START_DATE = "start_date";



    public static final String TABLE_SLEEP_TRACKS = "sleep_tracks";
    public static final String SLEEP_TRACKS_COLUMN_ID = "id";
    public static final String SLEEP_TRACKS_COLUMN_USERID = "user_id";
    public static final String SLEEP_TRACKS_COLUMN_DIARY_DATE = "diary_date";
    public static final String SLEEP_TRACKS_COLUMN_INBED_TIME = "in_bed_time";
    public static final String SLEEP_TRACKS_COLUMN_SLEEP_TIME = "sleep_time";
    public static final String SLEEP_TRACKS_COLUMN_WAKEUP_TIME = "wake_up_time";
    public static final String SLEEP_TRACKS_COLUMN_OUTBED_TIME = "out_bed_time";
    public static final String SLEEP_TRACKS_COLUMN_SLEEP_QUALITY = "sleep_quality";
    public static final String SLEEP_TRACKS_COLUMN_AWAKE_COUNT = "awake_count";
    public static final String SLEEP_TRACKS_COLUMN_TOTAL_AWAKE_TIME = "total_awake_time";
    public static final String SLEEP_TRACKS_COLUMN_SLEEP_LATENCY = "sleep_latency";
    public static final String SLEEP_TRACKS_COLUMN_CREATE_TIME = "create_time";




    public static final String TABLE_ACTIVITY_TRACKS = "activity_tracks";
    public static final String ACTIVITY_TRACKS_COLUMN_ID = "id";
    public static final String ACTIVITY_TRACKS_COLUMN_ACTIVITYID = "activity_id";
    public static final String ACTIVITY_TRACKS_COLUMN_USERID = "user_id";
    public static final String ACTIVITY_TRACKS_COLUMN_RECORDTYPE = "record_type";
    public static final String ACTIVITY_TRACKS_COLUMN_STARTTIME = "start_time";
    public static final String ACTIVITY_TRACKS_COLUMN_ENDTIME = "end_time";
    public static final String ACTIVITY_TRACKS_COLUMN_CREATETIME = "create_time";



    public static final String TABLE_ACTIVITIES = "activities";
    public static final String ACTIVITIES_COLUMN_ID = "id";
    public static final String ACTIVITIES_COLUMN_USERID = "user_id";
    public static final String ACTIVITIES_COLUMN_COLOR = "color";
    public static final String ACTIVITIES_COLUMN_POSITION = "position";
    public static final String ACTIVITIES_COLUMN_ACTIVITY_NAME = "activity_name";
    public static final String ACTIVITIES_COLUMN_DEFAULTX = "defaultx";



    public static final String TABLE_SLEEP_DISTURBANCES = "sleep_disturbances";
    public static final String SLEEP_DISTURBANCES_COLUMN_ID = "id";
    public static final String SLEEP_DISTURBANCES_COLUMN_DISTURBANCENAME = "disturbance_name";
    public static final String SLEEP_DISTURBANCES_COLUMN_USERID = "user_id";



    public static final String TABLE_SLEEP_RITUALS = "sleep_rituals";
    public static final String SLEEP_RITUALS_COLUMN_ID = "id";
    public static final String SLEEP_RITUALS_COLUMN_RITUALNAME = "ritual_name";
    public static final String SLEEP_RITUALS_COLUMN_USERID = "user_id";



    public static final String TABLE_SLEEP_TRACK_DISTURBANCES = "sleep_track_disturbances";
    public static final String SLEEP_TRACK_DISTURBANCES_COLUMN_ID = "id";
    public static final String SLEEP_TRACK_DISTURBANCES_COLUMN_DISTURBANCEID = "sleep_disturbance_id";
    public static final String SLEEP_TRACK_DISTURBANCES_COLUMN_SLEEP_TRACKID = "sleep_track_id";



    public static final String TABLE_SLEEP_TRACK_RITUALS = "sleep_track_rituals";
    public static final String SLEEP_TRACK_RITUALS_COLUMN_ID = "id";
    public static final String SLEEP_TRACK_RITUALS_COLUMN_RITUAL_ID = "sleep_ritual_id";
    public static final String SLEEP_TRACK_RITUALS_COLUMN_SLEEP_TRACKID = "sleep_track_id";




    private static final String TABLE_CREATE_USERS = "Create Table users (id int primary key AUTO_INCREMENT, " +
            "nickname text not null, passwd text not null, gender int, start_date date);";

    private static final String TABLE_CREATE_SLEEP_TRACKS = "Create Table sleep_tracks (id int primary key AUTO_INCREMENT, " +
            "user_id int REFERENCES users (id), diary_date DATE, in_bed_time TIME[ (P)] [WITHOUT TIME ZONE], " +
            "sleep_time TIME[ (P)] [WITHOUT TIME ZONE], wake_up_time TIME[ (P)] [WITHOUT TIME ZONE], " +
            "out_bed_time TIME[ (P)] [WITHOUT TIME ZONE], sleep_quality NUMERIC(5, 5), awake_count INT, " +
            "total_awake_time INTERVAL[FIELDS] [(P) ], sleep_latency INTERVAL[FIELDS] [(P) ]," +
            "create_time TIME[ (P)] [WITHOUT TIME ZONE]);";

    private static final String TABLE_CREATE_ACTIVITIES = "Create Table activities (id INT PRIMARY KEY AUTO_INCREMENT," +
            "user_id INT REFERENCES users (id),color TEXT, position INT" +
            "activity_name TEXT, defaultx TEXT);";

    private static final String TABLE_CREATE_ACTIVITY_TRACKS = "Create Table activity_tracks (id INT PRIMARY KEY AUTO_INCREMENT," +
            "activity_id INT REFERENCES activities (id),user_id INT REFERENCES users (id)," +
            "record_type TEXT, start_time TIME, end_time TIME, create_time TIMESTAMP);";




    private static final String TABLE_CREATE_SLEEP_DISTURBANCES = "Create Table sleep_disturbances (id INT PRIMARY KEY AUTO_INCREMENT," +
            "disturbance_name TEXT, user_id INT REFERENCES users (id));";

    private static final String TABLE_CREATE_SLEEP_RITUALS = "Create Table sleep_rituals (id INT PRIMARY KEY AUTO_INCREMENT," +
            "ritual_name TEXT, user_id INT REFERENCES users (id));";

    private static final String TABLE_CREATE_SLEEP_TRACK_DISTURBANCES = "Create Table sleep_track_disturbances (id INT PRIMARY KEY AUTO_INCREMENT," +
            "sleep_disturbance_id INT REFERENCES sleep_disturbance (id), sleep_track_id INT REFERENCES sleep_tracks (id));";

    private static final String TABLE_CREATE_SLEEP_TRACK_RITUALS = "Create Table sleep_track_rituals (id INT PRIMARY KEY AUTO_INCREMENT," +
            "sleep_ritual_id INT REFERENCES sleep_rituals (id), sleep_track_id INT REFERENCES sleep_tracks (id));";





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
