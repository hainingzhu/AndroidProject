package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class sleep_disturbances {
    private int id;
    private String disturbance_name;
    private int user_id;


    public sleep_disturbances ( String disturbance_name, int user_id) {
        this.disturbance_name = disturbance_name;
        this.user_id = user_id;
    }
}
