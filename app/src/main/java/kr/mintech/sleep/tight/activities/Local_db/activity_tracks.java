package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class activity_tracks {

    private int id;
    private String activity_id;
    private String user_id;
    private int record_type;
    private String start_time;
    private String end_time;
    private String create_time;



    public activity_tracks  (String activity_id, String user_id, int record_type,
                 String start_time, String end_time, String create_time)
    {
        this. activity_id = activity_id;
        this. user_id = user_id;
        this. record_type = record_type;
        this. start_time = start_time;
        this. end_time = end_time;
        this. create_time = create_time;
    }

}
