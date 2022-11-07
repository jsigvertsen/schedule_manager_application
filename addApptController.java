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
import sigvertsen.c195.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;

/** This is the Add Appointment Controller for the Appointment Manager Application*/
public class addApptController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private TableView<Appointment> addAppt_appointment_tbl;
    @FXML
    private TableColumn<Appointment, Integer> addAppt_id_col;
    @FXML
    private TableColumn<Appointment, String> addAppt_title_col;
    @FXML
    private TableColumn<Appointment, String> addAppt_desc_col;
    @FXML
    private TableColumn<Appointment, String> addAppt_location_col;
    @FXML
    private TableColumn<Appointment, String> addAppt_contact_col;
    @FXML
    private TableColumn<Appointment, String> addAppt_type_col;
    @FXML
    private TableColumn<Appointment, LocalDateTime> addAppt_start_col;
    @FXML
    private TableColumn<Appointment, LocalDateTime> addAppt_end_col;
    @FXML
    private TableColumn<Appointment, Integer> addAppt_customer_id_col;
    @FXML
    private TableColumn<Appointment, Integer> addAppt_user_id;
    @FXML
    private Label addAppt_error_msg;
    @FXML
    private TextField addAppt_title;
    @FXML
    private TextField addAppt_desc;
    @FXML
    private TextField addAppt_location;
    @FXML
    private ComboBox<String> addAppt_contact;
    @FXML
    private ComboBox<String> addAppt_customer;
    @FXML
    private TextField addAppt_type;
    @FXML
    private ComboBox<String> addAppt_startTime;
    @FXML
    private ComboBox<String> addAppt_end_time;
    @FXML
    private DatePicker addAppt_start_date;
    @FXML
    private DatePicker addAppt_end_date;
    @FXML
    private Button addAppt_save_btn;
    @FXML
    private final ObservableList<String> contactList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> customerList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> weekList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> monthList = FXCollections.observableArrayList();


    /** This is the initialize method
     * a lambda expression is used to add each contact and customer to their respective list and set the lists to their
     * respective combobox.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addAppt_appointment_tbl.setItems(Planner.getAllAppointments());
        addAppt_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        addAppt_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        addAppt_desc_col.setCellValueFactory(new PropertyValueFactory<>("desc"));
        addAppt_location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        addAppt_contact_col.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
        addAppt_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        addAppt_start_col.setCellValueFactory(new PropertyValueFactory<>("start_time_formatted"));
        addAppt_end_col.setCellValueFactory(new PropertyValueFactory<>("end_time_formatted"));
        addAppt_customer_id_col.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        addAppt_user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        // lambda 1
        Planner.getAllContacts().forEach( (i) -> { contactList.add(i.getName()); } );
        addAppt_contact.setItems(contactList.sorted());

        Planner.getAllCustomers().forEach( (i) -> { customerList.add(i.getName()); } );
        addAppt_customer.setItems(customerList);

        addAppt_startTime.setItems(Planner.getTimeList());

        addAppt_end_time.setItems(Planner.getTimeList());

        addAppt_start_date.setValue(java.time.LocalDate.now());

        addAppt_end_date.setValue(java.time.LocalDate.now());
    }

    /** This is the event handler for the all view radio button
     * All appointments are displayed in this setting
     * */
    public void onAction_all_view(ActionEvent event) {
        addAppt_appointment_tbl.setItems(Planner.getAllAppointments());
    }

    /** This is the event handler for the week view radio button
     * Appointments in the current week are displayed in this setting
     * */
    public void onAction_week_view(ActionEvent event) {
        weekList.clear();
        for (Appointment appointment : Planner.getAllAppointments()) {

            appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR);

            if (appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR) == LocalDateTime.now().get(ALIGNED_WEEK_OF_YEAR)) {
                weekList.add(appointment);
            }
        }
        addAppt_appointment_tbl.setItems(weekList);
    }

    /** This is the event handler for the month view radio button
     * Appointments in the current month are displayed in this setting
     * */
    public void onAction_month_view(ActionEvent event) {
        monthList.clear();
        for (Appointment appointment : Planner.getAllAppointments()) {
            if (appointment.getStart_time().getMonth().equals(LocalDateTime.now().getMonth())) {
                monthList.add(appointment);
            }
        }
        addAppt_appointment_tbl.setItems(monthList);
    }

    /** This is the event handler for the save button
     * All fields are checked for values and are inserted into the sql database
     * Appointments are checked for collisions
     * */
    public void onAction_addAppt_save(ActionEvent event) throws SQLException {
        try {

            // if any fields are empty
            if (addAppt_title.getText().isEmpty() ||
                    addAppt_desc.getText().isEmpty() ||
                    addAppt_location.getText().isEmpty() ||
                    addAppt_type.getText().isEmpty() ||
                    addAppt_start_date.getValue() == null ||
                    addAppt_startTime.getValue() == null ||
                    addAppt_end_date.getValue() == null ||
                    addAppt_end_time.getValue() == null ||
                    addAppt_contact.getValue() == null ||
                    addAppt_customer.getValue() == null) {
                throw new EmptyFieldException();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddhh:mm a");

            // get values from fields
            String c1 = addAppt_title.getText();
            String c2 = addAppt_desc.getText();
            String c3 = addAppt_location.getText();
            String c4 = addAppt_type.getText();
            ZonedDateTime c5 = LocalDateTime.parse(addAppt_start_date.getValue() + addAppt_startTime.getValue(), formatter).atZone(ZoneId.of("UTC"));
            ZonedDateTime c6 = LocalDateTime.parse(addAppt_end_date.getValue() + addAppt_end_time.getValue(), formatter).atZone(ZoneId.of("UTC"));
            String c7 = String.valueOf(addAppt_contact.getValue());
            String c8 = String.valueOf(addAppt_customer.getValue());

            if (c6.isBefore(c5)) {
                throw new AppointmentTimingException();
            }

            for (Appointment appointment : Planner.getAllAppointments()) {

                if (appointment.getCustomer_id() == CustomerQuery.selectCustomerId(c8)) {

                    ZonedDateTime start = ZonedDateTime.of(appointment.getStart_time(), ZoneId.of("UTC"));
                    ZonedDateTime end = ZonedDateTime.of(appointment.getEnd_time(), ZoneId.of("UTC"));

                    // all possible appointment conflicts
                    if (c5.isAfter(start) && c5.isBefore(end)) {
                        throw new AppointmentConflict();
                    }
                    if (c5.equals(start) && c6.isAfter(c5)) {
                        throw new AppointmentConflict();
                    }
                    if (c6.equals(end) && c5.isBefore(start)) {
                        throw new AppointmentConflict();
                    }
                    if (c6.isAfter(start) && c6.isBefore(end)) {
                        throw new AppointmentConflict();
                    }
                    if (c5.isAfter(start) && c6.isBefore(end)) {
                        throw new AppointmentConflict();
                    }
                    if (start.isAfter(c5) && end.isBefore(c6)) {
                        throw new AppointmentConflict();
                    }
                    if (start.equals(c5) && end.equals(c6)) {
                        throw new AppointmentConflict();
                    }
                }
            }

            AppointmentQuery.insert(c1, c2, c3, c4, c5, c6, c7, c8);

            AppointmentQuery.select("SELECT * FROM client_schedule.appointments");
            addAppt_error_msg.setText("Appointment successfully added.");
        }
        catch (AppointmentTimingException e) {
            addAppt_error_msg.setText("Appointment not added. Start Time is after End Time");
        }
        catch (AppointmentConflict e) {
            addAppt_error_msg.setText("Appointment not added. Appointment conflicts with another appointment for Customer: " + addAppt_customer.getValue());
        }
        catch (EmptyFieldException e) {
            addAppt_error_msg.setText("Appointment not added. All fields must be filled.");
        }

    }

    /** This is the event handler for the clear button
     * All fields are set to their default value
     * */
    public void onAction_addAppt_clear(ActionEvent event) {

        // set values to their default values
        addAppt_title.setText("");
        addAppt_desc.setText("");
        addAppt_location.setText("");
        addAppt_type.setText("");
        addAppt_contact.setValue("Select Contact");
        addAppt_customer.setValue("Select Customer");
        addAppt_type.setText("");
        addAppt_startTime.setValue("Select Start Time");
        addAppt_start_date.setValue(LocalDate.now());
        addAppt_end_time.setValue("Select End Time");
        addAppt_end_date.setValue(LocalDate.now());
    }

    /** This is the event handler for the back to home button
     * The user is sent back to the home screen
     * */
    public void onAction_back_to_home(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/main.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointment Manager - Home");
        stage.show();
    }

    static class AppointmentConflict extends Exception {
        public AppointmentConflict() {}
    }
    static class AppointmentTimingException extends Exception {
        public AppointmentTimingException() {}
    }
    static class EmptyFieldException extends Exception {
        public EmptyFieldException() {}
    }
}
