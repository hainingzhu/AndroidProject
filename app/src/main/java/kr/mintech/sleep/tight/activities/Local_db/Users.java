package kr.mintech.sleep.tight.activities.Local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.mintech.sleep.tight.networks.JsonNode;

/**
 * Created by hbz5037 on 2/19/16.
 */
public class Users {

    public int id;
    public String loginName;
    public int birthYear;
    public String gender;
    public String sleepCondition;
    public String start_date;



    public Users (int uid, String name,  int birthYear, String sex, String sleepCondition) {
        id = uid;
        loginName = name;
        this.birthYear = birthYear;
        gender = sex;
        this.sleepCondition = sleepCondition;
        this.start_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }


    @Override
    public String toString()
    {
        return String.format("Id: %d, User: %s, BirthYear: %d, gender: %s, SleepCondition: %s, startDate: %s",
                id, loginName, birthYear, gender, sleepCondition, start_date);
    }

}
