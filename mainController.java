package sigvertsen.c195.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sigvertsen.c195.helper.JDBC;
import sigvertsen.c195.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;

/** This is the Main Controller for the Appointment Manager Application*/
public class mainController implements Initializable {

    @FXML
    private TableView<Appointment> main_appointment_tbl;
    @FXML
    private TableColumn<Appointment, Integer> main_appts_appt_id_col;
    @FXML
    private TableColumn<Appointment, String> main_appts_appt_title_col;
    @FXML
    private TableColumn<Appointment, String> main_appts_desc_col;
    @FXML
    private TableColumn<Appointment, String> main_appts_location_col;
    @FXML
    private TableColumn<Appointment, String> main_appts_contact_col;
    @FXML
    private TableColumn<Appointment, String> main_appts_type_col;
    @FXML
    private TableColumn<Appointment, LocalDateTime> main_appts_start_col;
    @FXML
    private TableColumn<Appointment, LocalDateTime> main_appts_end_col;
    @FXML
    private TableColumn<Appointment, Integer> main_appts_customer_id_col;
    @FXML
    private TableColumn<Appointment, Integer> main_appts_user_id_col;
    @FXML
    private TableView<Customer> main_customer_tbl;
    @FXML
    private TableColumn<Customer, String> main_customer_name;
    @FXML
    private TableColumn<Customer, String> main_customer_address;
    @FXML
    private TableColumn<Customer, String> main_customer_postal_code;
    @FXML
    private TableColumn<Customer, String> main_customer_phone;

    Stage stage;
    Parent scene;
    Appointment selectedRow;
    ResourceBundle rb = ResourceBundle.getBundle("/nat", Locale.getDefault());
    @FXML
    private final ObservableList<String> contactList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> allList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> monthList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> weekList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> months = FXCollections.observableArrayList();
    @FXML
    public Label main_upcoming_appointments_lbl;
    @FXML
    public Label main_error_msg_lbl;
    @FXML
    public ComboBox<String> main_contact_filter;
    @FXML
    public RadioButton main_all_radio;
    @FXML
    public RadioButton main_week_radio;
    @FXML
    public RadioButton main_month_radio;
    @FXML
    public TextField main_customer_search;
    @FXML
    public Label main_total_lbl;
    @FXML
    public ComboBox<String> main_type_select;
    @FXML
    public ComboBox<String> main_month_select;

    /** This is the initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppointmentQuery.select("SELECT * FROM client_schedule.appointments");
        CustomerQuery.select("SELECT * FROM client_schedule.customers");
        ContactQuery.select("SELECT * FROM client_schedule.contacts");
        AppointmentQuery.selectAppointmentByType();

        main_appointment_tbl.setItems(Planner.getAllAppointments());
        main_appts_appt_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        main_appts_appt_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        main_appts_desc_col.setCellValueFactory(new PropertyValueFactory<>("desc"));
        main_appts_location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        main_appts_contact_col.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
        main_appts_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        main_appts_start_col.setCellValueFactory(new PropertyValueFactory<>("start_time_formatted"));
        main_appts_end_col.setCellValueFactory(new PropertyValueFactory<>("end_time_formatted"));
        main_appts_customer_id_col.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        main_appts_user_id_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        main_customer_tbl.setItems(Planner.getAllCustomers());
        main_customer_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        main_customer_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        main_customer_postal_code.setCellValueFactory(new PropertyValueFactory<>("zip"));
        main_customer_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        StringBuilder text_to_display = new StringBuilder();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // checks for appointments within 15 minutes of current time
        for (Appointment appointment : Planner.getAllAppointments()) {

            Duration timeElapsed = Duration.between(appointment.getStart_time(), LocalDateTime.now());

            long timeElapsedMinutes = timeElapsed.toMinutes();

            if (timeElapsedMinutes < 0 && timeElapsedMinutes > -15) {
                String text = "Appointment " + appointment.getId() + " from " +
                        appointment.getStart_time().toLocalTime().format(timeFormat) +
                        " to " + appointment.getEnd_time().toLocalTime().format(timeFormat) + " on " +
                        appointment.getStart_time().toLocalDate().format(dateFormat) + "\n";

                text_to_display.append(text);
            }
        }

        if (text_to_display.isEmpty()) { // if there are no appointments
            main_upcoming_appointments_lbl.setText("No upcoming appointments.");
        }
        else { // if there are appointments
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have upcoming appointments!");
            Optional<ButtonType> result = alert.showAndWait();
            main_upcoming_appointments_lbl.setText(String.valueOf(text_to_display));
        }

        // set items for the contact combobox
        for (Contact contact : Planner.getAllContacts()) {
            contactList.add(contact.getName());
        }
        main_contact_filter.setItems(contactList);
    }

    /** This is the event handler for the Add Appointment button
     * Sends the user to the Add Appointment page
     * */
    public void onAction_add_appt(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/addAppt.fxml")));
        stage.setTitle("Appointment Manager - Add Appointment");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This is the event handler for the contact filter
     * Filters the appointment table based on the contact selected
     * */
    public void onAction_filter_contact(ActionEvent event) {
        if (main_all_radio.isSelected()) { // if all radio button selected
            allList.clear();
            for (Appointment appointment : Planner.getAllAppointments()) {

                if (appointment.getContact_name().equals(main_contact_filter.getValue())) {
                    allList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(allList);
        }
        if (main_week_radio.isSelected()) { // if week radio button selected
            weekList.clear();
            for (Appointment appointment : Planner.getAllAppointments()) {

                appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR);

                if (appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR) == LocalDateTime.now().get(ALIGNED_WEEK_OF_YEAR) &&
                        appointment.getContact_name().equals(main_contact_filter.getValue())) {
                    weekList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(weekList);
        }
        if (main_month_radio.isSelected()) { // if month radio button selected
            monthList.clear();
            for (Appointment appointment : Planner.getAllAppointments()) {
                if (appointment.getStart_time().getMonth().equals(LocalDateTime.now().getMonth()) &&
                        appointment.getContact_name().equals(main_contact_filter.getValue())) {
                    monthList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(monthList);
        }
    }

    /** This is the event handler for the all view radio button
     * Displays all appointments in the appointment table
     * */
    public void onAction_all_view(ActionEvent event) {
        if (main_contact_filter.getValue() == null) {
            main_appointment_tbl.setItems(Planner.getAllAppointments());
        }
        else {
            allList.clear();
            for (Appointment appointment : Planner.getAllAppointments()) {

                if (appointment.getContact_name().equals(main_contact_filter.getValue())) {
                    allList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(allList);
        }
    }

    /** This is the event handler for the week view radio button
     * Displays all appointments matching the current week in the appointment table
     * */
    public void onAction_week_view(ActionEvent event) {
        weekList.clear();
        if (main_contact_filter.getValue() == null) { // if no contact selected
            for (Appointment appointment : Planner.getAllAppointments()) {

                appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR);

                if (appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR) == LocalDateTime.now().get(ALIGNED_WEEK_OF_YEAR)) {
                    weekList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(weekList);
        }
        else { // if contact selected
            for (Appointment appointment : Planner.getAllAppointments()) {

                appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR);

                if (appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR) == LocalDateTime.now().get(ALIGNED_WEEK_OF_YEAR) &&
                appointment.getContact_name().equals(main_contact_filter.getValue())) {
                    weekList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(weekList);
        }
    }

    /** This is the event handler for the month view radio button
     * Displays all appointments matching the current month in the Appointments table
     * */
    public void onAction_month_view(ActionEvent event) {
        monthList.clear();

        if (main_contact_filter.getValue() == null) { // if no contact selected

            for (Appointment appointment : Planner.getAllAppointments()) {
                if (appointment.getStart_time().getMonth().equals(LocalDateTime.now().getMonth())) {
                    monthList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(monthList);
        }
        else { // if contact selected
            for (Appointment appointment : Planner.getAllAppointments()) {
                if (appointment.getStart_time().getMonth().equals(LocalDateTime.now().getMonth()) &&
                        appointment.getContact_name().equals(main_contact_filter.getValue())) {
                    monthList.add(appointment);
                }
            }
            main_appointment_tbl.setItems(monthList);
        }
    }

    /** This is the event handler for the modify appointment button
     * Sends the user to the modify appointment page
     * */
    public void onAction_modify_appt(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/modifyAppt.fxml")));
        stage.setTitle("Appointment Manager - Modify Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the event handler for the contact search field
     * filters the contact table if input matches name or id
     * */
    public void onAction_contact_search(ActionEvent event) {
        try {
            main_error_msg_lbl.setText(""); // reset error label
            String userInputCustomer = main_customer_search.getText();

            main_customer_tbl.setItems(Planner.lookupCustomer(userInputCustomer));

            System.out.println(userInputCustomer);

            if (Planner.lookupCustomer(userInputCustomer).size() == 0) { // if no matches
                main_error_msg_lbl.setText("No customers found.");
            }
        }
        catch (NumberFormatException e) { // nothing in search bar
            main_customer_tbl.setItems(Planner.getAllCustomers());
        }
    }

    /** This is the event handler for the Add Contact button
     * The user is sent to the Add Contact page
     * */
    public void onAction_add_contact(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/addCustomer.fxml")));
        stage.setTitle("Appointment Manager - Add Customer");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the event handler for the Modify Contact button
     * The user is sent to the Modify Contact page
     * */
    public void onAction_modify_contact(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/modifyCustomer.fxml")));
        stage.setTitle("Appointment Manager - Modify Customer");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the event handler for the Logout button
     * The user is sent to the login page
     * */
    public void onAction_logout(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/login.fxml")));
        stage.setTitle(rb.getString("page_title"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the event handler for the Reports button
     * The user is sent to the reports page
     * */
    public void onAction_reports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/reports.fxml")));
        stage.setTitle("Appointment Manager - Reports");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    static class NullDeleteException extends Exception {
        public NullDeleteException() {}
    }
}
