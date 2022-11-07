package sigvertsen.c195.controller;

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
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;


/** This is the Reports Controller for the Appointment Manager Application*/
public class reportsController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private ComboBox<String> reports_month_combo1;
    @FXML
    private ComboBox<String> reports_month_combo2;
    @FXML
    private Label reports_error_msg;
    @FXML
    private TableView<ReportMonthType> reports_type_month_tbl;
    @FXML
    private TableColumn<ReportMonthType, String> reports_type_col;
    @FXML
    private TableColumn<ReportMonthType, Month> reports_month_col;
    @FXML
    private TableColumn<ReportMonthType, Integer> reports_count_col;
    @FXML
    private TableView<ReportContactMonth> reports_contacts_tbl;
    @FXML
    private TableColumn<ReportContactMonth, Month> reports_month2_col;
    @FXML
    private TableColumn<ReportContactMonth, String> reports_contact_col;
    @FXML
    private TableColumn<ReportContactMonth, Integer> reports_count_appt_col;

    /** This is the initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AppointmentQuery.selectAppointmentByType();

        // set months for month combos
        reports_month_combo1.setItems(Planner.getMonthList());
        reports_month_combo2.setItems(Planner.getMonthList());
    }

    /** This is the event handler for the Number of Appointments by month for contact report
     * Returns results based on month selected
     * */
    public void onAction_num_appts_by_month(ActionEvent event) {
        Report.clearContactMonthRecords();

        String month = reports_month_combo2.getValue();

        AppointmentQuery.reportContactAndMonth(month);

        reports_contacts_tbl.setItems(Report.getContactMonthRecords());
        reports_month2_col.setCellValueFactory(new PropertyValueFactory<>("month"));
        reports_contact_col.setCellValueFactory(new PropertyValueFactory<>("contact"));
        reports_count_appt_col.setCellValueFactory(new PropertyValueFactory<>("num_appts"));
    }

    /** This is the event handler for the type and month report
     * Returns results based on month selected
     * */
    public void onAction_type_month_report(ActionEvent event) {

        Report.clearMonthTypeRecords();

        String month = reports_month_combo1.getValue();

        AppointmentQuery.reportMonthAndType(month);

        reports_type_month_tbl.setItems(Report.getMonthTypeRecords());
        reports_month_col.setCellValueFactory(new PropertyValueFactory<>("month"));
        reports_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        reports_count_col.setCellValueFactory(new PropertyValueFactory<>("type_count"));
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
}
