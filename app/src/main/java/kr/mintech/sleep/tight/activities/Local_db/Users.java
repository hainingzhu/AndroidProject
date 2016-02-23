package kr.mintech.sleep.tight.activities.Local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import kr.mintech.sleep.tight.networks.JsonNode;

/**
 * Created by hbz5037 on 2/19/16.
 */
public class Users {

    private int id;
    private String loginName;
    public int birthYear;
    private String gender;
    public String sleepCondition;
    private String start_date;



    public Users (String name,  int birthYear, String sex, String sleepCondition, String date) {
        loginName = name;
        this.birthYear = birthYear;
        gender = sex;
        this.sleepCondition = sleepCondition;
        start_date = date;
    }


}
