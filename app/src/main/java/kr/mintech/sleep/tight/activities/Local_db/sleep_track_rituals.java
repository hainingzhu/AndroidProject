package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class sleep_track_rituals {
    public int id;
    public int sleep_ritual_id;
    public int sleep_track_id;


    public sleep_track_rituals ( int sleep_ritual_id, int sleep_track_id) {
        this.sleep_ritual_id = sleep_ritual_id;
        this.sleep_track_id = sleep_track_id;
    }


}
