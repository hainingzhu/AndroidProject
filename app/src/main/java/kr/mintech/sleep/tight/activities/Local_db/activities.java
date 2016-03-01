package kr.mintech.sleep.tight.activities.Local_db;

import android.util.Log;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class activities {
    public int id;
    public int user_id;
    public String activity_name;
    //public int defaultType;
    public int isHide;



    public activities ( int user_id, int activitiyId, String name, int isHide) {
        this.id = activitiyId;
        this.user_id = user_id;
        //this.defaultType = type;
        this.activity_name = name;

        this.isHide = isHide;
    }

    @Override
    public String toString()
    {
        return String.format("activityID: %d, user_id: %d, activity_name: %s, isHide: %d",
                id, user_id, activity_name, isHide);
    }

}

