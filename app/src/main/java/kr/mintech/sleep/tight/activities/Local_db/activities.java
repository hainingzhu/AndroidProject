package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class activities {
    public int id;
    //private int activitiyId;
    public int user_id;
    public String color;
    public int position;
    public String activity_name;
    public int defaultType;
    public int isHide;



    public activities ( int user_id, String color, int position, String activity_name, int defaultType, int isHide) {
        //this.activitiyId = activitiyId;
        this.user_id = user_id;
        this.color = color;
        this.position = position;
        this.activity_name = activity_name;
        this.defaultType = defaultType;
        this.isHide = isHide;
    }

    @Override
    public String toString()
    {
        return String.format("id: %d, user_id: %d, color: %s, position: %d, activity_name: %s, defaultType: %d, isHide: %d",
                id, user_id, color, position, activity_name, defaultType, isHide);
    }

}

