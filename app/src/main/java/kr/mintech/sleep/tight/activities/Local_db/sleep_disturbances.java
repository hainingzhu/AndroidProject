package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 * CHECK units/EditSleepDisturbUnit
 */
public class sleep_disturbances {
    public int id;
    public String disturbance_name;
    public int user_id;


    public sleep_disturbances ( String disturbance_name, int user_id) {
        this.disturbance_name = disturbance_name;
        this.user_id = user_id;
    }
}

