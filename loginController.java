package sigvertsen.c195.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sigvertsen.c195.helper.JDBC;
import sigvertsen.c195.model.Appointment;
import sigvertsen.c195.model.Planner;
import sigvertsen.c195.model.TimeZoneConversion;
import sigvertsen.c195.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the Login In controller for the Appointment Manager Application*/
public class loginController implements Initializable {

    Stage stage;

    Parent scene;

    ResourceBundle rb = ResourceBundle.getBundle("/nat", Locale.getDefault());

    @FXML
    private Button login_exit_btn;
    @FXML
    private Label login_location_lbl;
    @FXML
    private Label username_lbl;
    @FXML
    private Label password_lbl;
    @FXML
    private Label login_scheduling_system_lbl;
    @FXML
    private Label login_welcome_lbl;
    @FXML
    private Label login_error_msg_lbl;
    @FXML
    private TextField login_username_txt;
    @FXML
    private TextField login_password_txt;

    public static User user = null;

    /** This is the initialize method
     * Text is set based on default system language for the user
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        login_welcome_lbl.setText(rb.getString("welcome_message"));
        login_scheduling_system_lbl.setText(rb.getString("login_title"));
        username_lbl.setText(rb.getString("username_text"));
        password_lbl.setText(rb.getString("password_text"));
        login_exit_btn.setText(rb.getString("exit_btn_text"));

        String userTimezone = ZoneId.systemDefault().getId(); // get user time zone
        String userLanguage = Locale.getDefault().getDisplayLanguage(Locale.getDefault()); // get user language

        login_location_lbl.setText(userTimezone + " | " + userLanguage);
    }

    /** This is the event handler for the Password field
     * Based on user input for username and password. This checks for a match in the database for the username and
     * password entered
     * */
    @FXML
    void onAction_login_password_field(ActionEvent event) throws IOException {

        boolean login_switch = false;

        try {
            ResultSet resultSet = JDBC.connection.createStatement().executeQuery("SELECT User_ID, User_Name, Password FROM client_schedule.users");

            while (resultSet.next()) { // search database for matches to username and password

                int user_id = resultSet.getInt("User_ID");
                String user_name = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");

                if (login_username_txt.getText().equals(user_name) && login_password_txt.getText().equals(password)) {

                    login_switch = true;

                    user = new User(user_id, user_name);

                    stage = (Stage)((TextField)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/main.fxml")));
                    stage.setTitle("Appointment Manager - Home");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            if (!login_switch) { // if no matches
                throw new InvalidLogin();
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidLogin e) {
            login_error_msg_lbl.setText(rb.getString("invalid_login_msg"));
            LocalDateTime current = TimeZoneConversion.ConvertToZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"));

            // append to file login_activity.txt
            File login_activity = new File("login_activity.txt");
            FileWriter login_activity_append = new FileWriter(login_activity, true);
            login_activity_append.write(login_username_txt.getText() + "\t\t" +
                    current.toLocalDate() + "\t\t" +
                    current.toLocalTime() + "\t\t" +
                    "Fail\n");
            login_activity_append.close();
        }
    }

    /** This is the event handler for the exit button
     * This button closes the application
     * */
    @FXML
    void onAction_login_exit(ActionEvent event) {
        System.exit(0);
        JDBC.closeConnection();
    }

    static class InvalidLogin extends Exception {
        public InvalidLogin() { }
    }
}
