package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class activities {
    private int id;
    private int user_id;
    private String color;
    private int position;
    private String activity_name;
    private String defaultx;



    public activities ( int user_id, String color, int position, String activity_name, String defaultx) {
        this.user_id = user_id;
        this.color = color;
        this.position = position;
        this.activity_name = activity_name;
        this.defaultx = defaultx;
    }
}
