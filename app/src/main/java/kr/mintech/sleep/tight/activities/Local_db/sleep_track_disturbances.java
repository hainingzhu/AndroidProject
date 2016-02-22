package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class sleep_track_disturbances {
    private int id;
    private int sleep_disturbance_id;
    private int sleep_track_id;


    public sleep_track_disturbances ( int sleep_disturbance_id, int sleep_track_id) {
        this.sleep_disturbance_id = sleep_disturbance_id;
        this.sleep_track_id = sleep_track_id;
    }
}
