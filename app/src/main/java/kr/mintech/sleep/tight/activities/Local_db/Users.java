package kr.mintech.sleep.tight.activities.Local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hbz5037 on 2/19/16.
 */
public class Users {

    private int id;
    private String loginName;
    private String passwd;
    private int gender;
    private String start_date;



    public Users (String name, String pwd, int sex, String date) {
        loginName = name;
        passwd = pwd;
        gender = sex;
        start_date = date;
    }


}
