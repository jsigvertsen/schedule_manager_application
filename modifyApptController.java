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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;

/** This is the Modify Appointment Controller for the Appointment Manager Application*/
public class modifyApptController implements Initializable {

    Stage stage;
    Parent scene;
    Appointment selectedRow;
    @FXML
    private TableView<Appointment> modifyAppt_appointment_tbl;
    @FXML
    private TableColumn<Appointment, Integer> modifyAppt_id_col;
    @FXML
    private TableColumn<Appointment, String> modifyAppt_title_col;
    @FXML
    private TableColumn<Appointment, String> modifyAppt_desc_col;
    @FXML
    private TableColumn<Appointment, String> modifyAppt_location_col;
    @FXML
    private TableColumn<Appointment, String> modifyAppt_contact_col;
    @FXML
    private TableColumn<Appointment, String> modifyAppt_type_col;
    @FXML
    private TableColumn<Appointment, LocalDateTime> modifyAppt_start_col;
    @FXML
    private TableColumn<Appointment, LocalDateTime> modifyAppt_end_col;
    @FXML
    private TableColumn<Appointment, Integer> modifyAppt_customer_id_col;
    @FXML
    private TableColumn<Appointment, Integer> modifyAppt_user_id;
    @FXML
    private TextField modifyAppt_id;
    @FXML
    private TextField modifyAppt_title;
    @FXML
    private TextField modifyAppt_desc;
    @FXML
    private TextField modifyAppt_location;
    @FXML
    private ComboBox<String> modifyAppt_contact;
    @FXML
    private ComboBox<String> modifyAppt_customer;
    @FXML
    private TextField modifyAppt_type;
    @FXML
    private ComboBox<String> modifyAppt_startTime;
    @FXML
    private DatePicker modifyAppt_start_date;
    @FXML
    private ComboBox<String> modifyAppt_end_time;
    @FXML
    private DatePicker modifyAppt_end_date;
    @FXML
    private Label modifyAppt_error_msg;
    @FXML
    private Button modifyAppt_save_btn;
    @FXML
    private final ObservableList<String> contactList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> customerList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> weekList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Appointment> monthList = FXCollections.observableArrayList();

    /** This is the initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyAppt_appointment_tbl.setItems(Planner.getAllAppointments());
        modifyAppt_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyAppt_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        modifyAppt_desc_col.setCellValueFactory(new PropertyValueFactory<>("desc"));
        modifyAppt_location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        modifyAppt_contact_col.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
        modifyAppt_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        modifyAppt_start_col.setCellValueFactory(new PropertyValueFactory<>("start_time_formatted"));
        modifyAppt_end_col.setCellValueFactory(new PropertyValueFactory<>("end_time_formatted"));
        modifyAppt_customer_id_col.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        modifyAppt_user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        for (Contact contact : Planner.getAllContacts()) { // set contacts for contact list
            contactList.add(contact.getName());
        }

        modifyAppt_contact.setItems(contactList);

        for (Customer customer : Planner.getAllCustomers()) { // set customers for customer list
            customerList.add(customer.getName());
        }

        modifyAppt_customer.setItems(customerList);

        // set start and end time lists
        modifyAppt_startTime.setItems(Planner.getTimeList());
        modifyAppt_end_time.setItems(Planner.getTimeList());

        // disable all fields on entry
        modifyAppt_title.setDisable(true);
        modifyAppt_desc.setDisable(true);
        modifyAppt_desc.setDisable(true);
        modifyAppt_location.setDisable(true);
        modifyAppt_contact.setDisable(true);
        modifyAppt_customer.setDisable(true);
        modifyAppt_type.setDisable(true);
        modifyAppt_startTime.setDisable(true);
        modifyAppt_start_date.setDisable(true);
        modifyAppt_end_time.setDisable(true);
        modifyAppt_end_date.setDisable(true);
        modifyAppt_save_btn.setDisable(true);

    }

    /** This is the event handler for the Cancel Appointment method
     * Deletes the appointment from database
     * */
    public void onAction_cancel_appointment(ActionEvent event) {
        try {
            Appointment selectedRow = modifyAppt_appointment_tbl.getSelectionModel().getSelectedItem();

            if (selectedRow == null) { // if nothing selected
                throw new mainController.NullDeleteException();
            }

            AppointmentQuery.delete(selectedRow.getId()); // delete query
            modifyAppt_error_msg.setText("Appointment " + selectedRow.getId() + " " + selectedRow.getType() + " has been canceled.");
            AppointmentQuery.select("SELECT * FROM client_schedule.appointments");
        }
        catch (mainController.NullDeleteException e) {
            modifyAppt_error_msg.setText("Nothing selected. No appointments canceled.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the event handler for the all view radio button
     * Displays all appointments in the appointment table
     * */
    public void onAction_all_view(ActionEvent event) {
        modifyAppt_appointment_tbl.setItems(Planner.getAllAppointments());
    }

    /** This is the event handler for the week view radio button
     * Displays all appointments matching the current week in the appointment table
     * */
    public void onAction_week_view(ActionEvent event) {
        weekList.clear();
        for (Appointment appointment : Planner.getAllAppointments()) {

            appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR);

            if (appointment.getStart_time().get(ALIGNED_WEEK_OF_YEAR) == LocalDateTime.now().get(ALIGNED_WEEK_OF_YEAR)) {
                weekList.add(appointment);
            }
        }
        modifyAppt_appointment_tbl.setItems(weekList);
    }

    /** This is the event handler for teh Month View radio button
     * Displays all appointments matching the current month in the appointment table
     * */
    public void onAction_month_view(ActionEvent event) {
        monthList.clear();
        for (Appointment appointment : Planner.getAllAppointments()) {
            if (appointment.getStart_time().getMonth().equals(LocalDateTime.now().getMonth())) {
                monthList.add(appointment);
            }
        }
        modifyAppt_appointment_tbl.setItems(monthList);
    }

    /** This is the event handler for the Modify Appointment button
     * Enables all fields and sets values to selected appointment values
     * */
    public void onAction_modify_appt(ActionEvent event) {

        selectedRow = modifyAppt_appointment_tbl.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {

            modifyAppt_title.setDisable(false);
            modifyAppt_desc.setDisable(false);
            modifyAppt_desc.setDisable(false);
            modifyAppt_location.setDisable(false);
            modifyAppt_contact.setDisable(false);
            modifyAppt_customer.setDisable(false);
            modifyAppt_type.setDisable(false);
            modifyAppt_startTime.setDisable(false);
            modifyAppt_start_date.setDisable(false);
            modifyAppt_end_time.setDisable(false);
            modifyAppt_end_date.setDisable(false);
            modifyAppt_save_btn.setDisable(false);

            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            modifyAppt_id.setText(String.valueOf(selectedRow.getId()));
            modifyAppt_title.setText(selectedRow.getTitle());
            modifyAppt_desc.setText(selectedRow.getDesc());
            modifyAppt_location.setText(selectedRow.getLocation());
            modifyAppt_type.setText(selectedRow.getType());
            modifyAppt_startTime.setValue(selectedRow.getStart_time().format(timeFormat));
            modifyAppt_end_time.setValue(selectedRow.getEnd_time().format(timeFormat));
            modifyAppt_start_date.setValue(selectedRow.getStart_time().toLocalDate());
            modifyAppt_end_date.setValue(selectedRow.getEnd_time().toLocalDate());
            modifyAppt_contact.setValue(ContactQuery.selectContactName(selectedRow.getContact_id()));
            modifyAppt_customer.setValue(CustomerQuery.selectCustomerName(selectedRow.getCustomer_id()));
        }
    }

    /** This is the event handler for the save button
     * Saves all values in fields as long as there is a value present
     * */
    public void onAction_modifyAppt_save(ActionEvent event) throws SQLException {
        try {

            // if any fields empty
            if (modifyAppt_title.getText().isEmpty() ||
                    modifyAppt_desc.getText().isEmpty() ||
                    modifyAppt_location.getText().isEmpty() ||
                    modifyAppt_type.getText().isEmpty() ||
                    modifyAppt_start_date.getValue() == null ||
                    modifyAppt_startTime.getValue().isEmpty() ||
                    modifyAppt_end_date.getValue() == null ||
                    modifyAppt_end_time.getValue().isEmpty() ||
                    modifyAppt_contact.getValue() == null ||
                    modifyAppt_customer.getValue() == null) {
                throw new EmptyFieldException();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddhh:mm a");

            selectedRow = modifyAppt_appointment_tbl.getSelectionModel().getSelectedItem();

            String c1 = modifyAppt_title.getText();
            String c2 = modifyAppt_desc.getText();
            String c3 = modifyAppt_location.getText();
            String c4 = modifyAppt_type.getText();
            ZonedDateTime c5 = LocalDateTime.parse(modifyAppt_start_date.getValue() + modifyAppt_startTime.getValue(), formatter).atZone(ZoneId.of("UTC"));
            ZonedDateTime c6 = LocalDateTime.parse(modifyAppt_end_date.getValue() + modifyAppt_end_time.getValue(), formatter).atZone(ZoneId.of("UTC"));
            String c7 = String.valueOf(modifyAppt_contact.getValue());
            String c8 = String.valueOf(modifyAppt_customer.getValue());
            int c9 = Integer.parseInt(modifyAppt_id.getText());

            if (c6.isBefore(c5)) {
                throw new AppointmentTimingException();
            }

            for (Appointment appointment : Planner.getAllAppointments()) {

                // for all appointments other than selected appointment
                if (appointment.getId() != Integer.parseInt(modifyAppt_id.getText())) {

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

            AppointmentQuery.update(c1, c2, c3, c4, c5, c6, c7, c8, c9);

            modifyAppt_error_msg.setText("Appointment successfully updated.");

            AppointmentQuery.select("SELECT * FROM client_schedule.appointments");
        }
        catch (AppointmentTimingException e) {
            modifyAppt_error_msg.setText("Appointment not updated. Start Time is after End Time");
        }
        catch (AppointmentConflict e) {
            modifyAppt_error_msg.setText("Appointment not added. Appointment conflicts with another appointment for Customer: " + modifyAppt_customer.getValue());
        }
        catch (EmptyFieldException e) {
            modifyAppt_error_msg.setText("Appointment not updated. All fields must be filled.");
        }
    }

    public void onAction_modifyAppt_clear(ActionEvent event) {
    }

    /** This is the event handler for the back to home button
     * Sends the user back to the main page
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
