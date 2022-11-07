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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/** This is the Add Customer Controller for the Appointment Manager Application*/
public class addCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addCustomer_name_field;
    @FXML
    private TextField addCustomer_address_field;
    @FXML
    private TextField addCustomer_zip_field;
    @FXML
    private TextField addCustomer_phone_field;
    @FXML
    private ComboBox<String> addCustomer_country_field;
    @FXML
    private ComboBox<String> addCustomer_FLD_field;
    @FXML
    private Label addCustomer_error_msg;
    @FXML
    private Button addCustomer_add_btn;
    @FXML
    private TableView<Customer> addCustomer_tbl;
    @FXML
    private TableColumn<Customer, String> addCustomer_customer_name;
    @FXML
    private TableColumn<Customer, String> addCustomer_customer_address;
    @FXML
    private TableColumn<Customer, String> addCustomer_customer_postal_code;
    @FXML
    private TableColumn<Customer, String> addCustomer_customer_phone;
    @FXML
    private TableColumn<Customer, String> addCustomer_customer_FLD;
    @FXML
    private final ObservableList<String> countryList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> divisionList = FXCollections.observableArrayList();

    /** This is the initialize method
     * a lambda expression is used to add all countries to the country combobox
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCustomer_tbl.setItems(Planner.getAllCustomers());
        addCustomer_customer_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addCustomer_customer_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        addCustomer_customer_postal_code.setCellValueFactory(new PropertyValueFactory<>("zip"));
        addCustomer_customer_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addCustomer_customer_FLD.setCellValueFactory(new PropertyValueFactory<>("division_name"));

        CountryQuery.select();

        Planner.getAllCountries().forEach( (i) -> {countryList.add(i.getName()); } );

        addCustomer_country_field.setItems(countryList.sorted());
    }

    /** This is the event handler for the country combobox
     * The country selected determines the values set for the first level division combobox
     * */
    public void onAction_country_select(ActionEvent event) {

        divisionList.clear();

        AtomicInteger country_id = new AtomicInteger(-1);

        addCustomer_FLD_field.setDisable(false);

        // lambda 2
        Planner.getAllCountries().forEach( (i) -> {
            if(i.getName().equals(addCustomer_country_field.getValue())) { country_id.set(i.getId()); }
        });

        FirstLevelDivisionQuery.select(country_id.get());

        // get first level divisions
        for (FirstLevelDivision division : Planner.getAllDivisions()) {
            divisionList.add(division.getDivision());
        }

        // set first level divisions
        addCustomer_FLD_field.setItems(divisionList.sorted());
    }

    /** This is the event handler for the add contact button
     * All fields are checked for values and inserted in the sql database
     * */
    public void onAction_addContact_add(ActionEvent event) throws SQLException {
        try {

            // if any fields are empty
            if (addCustomer_name_field.getText().isEmpty() ||
                    addCustomer_address_field.getText().isEmpty() ||
                    addCustomer_zip_field.getText().isEmpty() ||
                    addCustomer_phone_field.getText().isEmpty() ||
                    addCustomer_country_field.getValue() == null ||
                    addCustomer_FLD_field.getValue() == null) {
                throw new EmptyFieldException();
            }

            // get values from fields
            String name = addCustomer_name_field.getText();
            String address = addCustomer_address_field.getText();
            String zip = addCustomer_zip_field.getText();
            String phone = addCustomer_phone_field.getText();
            String division_name = addCustomer_FLD_field.getValue();

            CustomerQuery.insert(name, address, zip, phone, division_name);

            CustomerQuery.select("SELECT * FROM client_schedule.customers");

            addCustomer_error_msg.setText("Customer successfully added.");
        }
        catch (EmptyFieldException e) {
            addCustomer_error_msg.setText("Customer not added. All fields must be filled.");
        }
    }

    /** This is the event handler for the clear button
     * All fields are set to the their default values
     * */
    public void onAction_addCustomer_clear(ActionEvent event) {

        // set fields to their default value
        addCustomer_name_field.setText("");
        addCustomer_address_field.setText("");
        addCustomer_phone_field.setText("");
        addCustomer_zip_field.setText("");
        addCustomer_country_field.valueProperty().set(null);
    }

    /** This is the event handler for the back to home button
     * The user is sent back to the home page
     * */
    public void onAction_back_to_home(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/sigvertsen/c195/view/main.fxml")));
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointment Manager - Home");
        stage.show();
    }
    static class EmptyFieldException extends Exception {
        public EmptyFieldException() {}
    }
}
