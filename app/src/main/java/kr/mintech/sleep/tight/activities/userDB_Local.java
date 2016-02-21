package kr.mintech.sleep.tight.activities;

/**
 * Created by hbz5037 on 2/19/16.
 */
public class userDB_Local {

        String userid, pwd;

        public void setId(String id)
        {
            this.userid = id;
        }

        public String getId()
        {
            return userid;
        }

        public void setPwd(String pass)
        {
            this.pwd = pass;
        }

        public String getPwd()
        {
            return pwd;
        }
    }

}
