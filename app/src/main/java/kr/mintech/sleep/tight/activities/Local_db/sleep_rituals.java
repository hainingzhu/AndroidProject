package kr.mintech.sleep.tight.activities.Local_db;

/**
 * Created by hbz5037 on 2/21/16.
 */
public class sleep_rituals {
    private int id;
    private String ritual_name;
    private int user_id;
    private int frequency;




    public sleep_rituals ( String ritual_name, int user_id, int frequency) {
        this.ritual_name = ritual_name;
        this.user_id = user_id;
        this.frequency = frequency;
    }
}
