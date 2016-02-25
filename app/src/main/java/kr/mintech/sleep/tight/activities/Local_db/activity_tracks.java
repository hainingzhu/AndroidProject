package kr.mintech.sleep.tight.activities.Local_db;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.mintech.sleep.tight.utils.DateTime;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class activity_tracks {

    public int id;
    public int activity_id;
    public int user_id;
    public String record_type;
    public String actionStartedAt;
    public String actionEndedAt;
    public String create_time;
    public String activityName;
    public String trackType;
                                    //public String actionStartedAtDateTime, actionEndedAtDateTime;
    public int sortPosition;
    public String color;


    public activity_tracks  (int activity_id, int user_id, String record_type, String actionStartedAt,
                             String actionEndedAt, String activityName, String trackType,
                             int sortPosition, String color)
    {
        this.activity_id = activity_id;
        this.user_id = user_id;
        this.record_type = record_type;
        this.actionStartedAt = actionStartedAt;
        this.actionEndedAt = actionEndedAt;
        this.create_time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").format(new Date());
        this.activityName = activityName;
        this.trackType = trackType;
        this.sortPosition = sortPosition;
        this.color = color;
    }


    @Override
    public String toString()
    {
        return String.format("Id: %d, ActivityId: %d, UserId: %d, recordType: %s, actionStartedAt: %s, " +
                        "actionEndedAt: %s, createTime: %s, activityName: %s, trackType: %s, " +
                        "sortPosition: %d, color: %s ",
                id, activity_id, user_id, record_type, actionStartedAt, actionEndedAt, create_time,
                activityName, trackType, sortPosition, color);
    }


}




