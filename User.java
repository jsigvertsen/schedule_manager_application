package sigvertsen.c195.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** class User.java*/
public class User {
    private int user_id;
    private String user_name;

    public User(int user_id, String user_name) throws IOException {

        this.user_id = user_id;
        this.user_name = user_name;

        // current time in UTC
        LocalDateTime current = TimeZoneConversion.ConvertToZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"));

        // append to file when user object is created
        File login_activity = new File("login_activity.txt");
        FileWriter login_activity_append = new FileWriter(login_activity, true);
        login_activity_append.write(
                user_name + "\t\t" +
                current.toLocalDate() + "\t\t" +
                current.toLocalTime() + "\t\t" +
                "Success\n");
        login_activity_append.close();
    }

    /**
     * a method to get the user id of a User Object
     * @return user_id is the user id to get
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * a method to set the user id to a User object
     * @param user_id is the user id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * a method to get the username of a User Object
     * @return user_name is the username to get
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * a method to set the username to a User object
     * @param user_name is the username to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


}
