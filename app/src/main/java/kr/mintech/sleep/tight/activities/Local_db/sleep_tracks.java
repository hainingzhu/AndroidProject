package kr.mintech.sleep.tight.activities.Local_db;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class sleep_tracks {

    public int id;
    public int user_id;
    public String diary_date;
    public String in_bed_time;
    public String sleep_time;
    public String wake_up_time;
    public String out_bed_time;
    public int sleep_quality;
    public int awake_count;
    public String total_awake_time;
    public String sleep_latency;
    public String create_time;
    public String sleepDuration;




        public sleep_tracks (int user_id, String in_bed_time, String sleep_time,
                             String wake_up_time, String out_bed_time, int sleep_quality, int awake_count,
                             String total_awake_time, String sleep_latency, String sleepDuration)
        {
            this.user_id = user_id;
            this.diary_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            this.in_bed_time = in_bed_time;
            this.sleep_time = sleep_time;
            this.wake_up_time = wake_up_time;
            this.out_bed_time = out_bed_time;
            this.sleep_quality = sleep_quality;
            this.awake_count = awake_count;
            this.total_awake_time = total_awake_time;
            this.sleep_latency = sleep_latency;
            this.create_time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            this.sleepDuration = sleepDuration;

        }
}

