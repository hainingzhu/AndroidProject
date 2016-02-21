package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class sleep_tracks {

    private int id;
    private int user_id;
    private String diary_date;
    private String in_bed_time;
    private String sleep_time;
    private String wake_up_time;
    private String out_bed_time;
    private int sleep_quality;
    private int awake_count;
    private String total_awake_time;
    private String sleep_latency;
    private String create_time;




        public sleep_tracks (int user_id, String diary_date, String in_bed_time, String sleep_time,
                             String wake_up_time, String out_bed_time, int sleep_quality, int awake_count,
                             String total_awake_time, String sleep_latency, String create_time)
        {
            this.user_id = user_id;
            this.diary_date = diary_date;
            this.in_bed_time = in_bed_time;
            this.sleep_time = sleep_time;
            this.wake_up_time = wake_up_time;
            this.out_bed_time = out_bed_time;
            this.sleep_quality = sleep_quality;
            this.awake_count = awake_count;
            this.total_awake_time = total_awake_time;
            this.sleep_latency = sleep_latency;
            this.create_time = create_time;

        }
}
