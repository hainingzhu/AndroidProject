package kr.mintech.sleep.tight.activities.Local_db;

import android.util.Log;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class activities {
    public int id;
    //private int activitiyId;
    public int user_id;
    public String activity_name;
    public int defaultType;
    public int isHide;



    public activities ( int user_id, int type,  int isHide) {
        //this.activitiyId = activitiyId;
        this.user_id = user_id;
        this.defaultType = type;
        switch (type) {
            case 38:
                this.activity_name = "meal"; break;
            case 39:
                this.activity_name = "exercise"; break;
            case 40:
                this.activity_name = "coffeine"; break;
            case 41:
                this.activity_name = "tobacco"; break;
            case 42:
                this.activity_name = "medication"; break;
            case 43:
                this.activity_name = "alcohol"; break;
            default:
                Log.w("WHJ", "The activity ID does not match!");
        }

        this.isHide = isHide;
    }

    @Override
    public String toString()
    {
        return String.format("id: %d, user_id: %d, activity_name: %s, defaultType: %d, isHide: %d",
                id, user_id, activity_name, defaultType, isHide);
    }

}

