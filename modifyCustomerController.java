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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the Modify Customer Controller for the Appointment Manager Application*/
public class modifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    Customer selectedRow;
    @FXML
    private TextField modifyCustomer_id_field;
    @FXML
    private TextField modifyCustomer_name_field;
    @FXML
    private TextField modifyCustomer_address_field;
    @FXML
    private TextField modifyCustomer_zip_field;
    @FXML
    private TextField modifyCustomer_phone_field;
    @FXML
    private ComboBox<String> modifyCustomer_country_field;
    @FXML
    private ComboBox<String> modifyCustomer_FLD_field;
    @FXML
    private Label modifyCustomer_error_msg;
    @FXML
    private Button modifyCustomer_save_btn;
    @FXML
    private final ObservableList<String> countryList = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> divisionList = FXCollections.observableArrayList();
    @FXML
    private TableView<Customer> modifyCustomer_tbl;
    @FXML
    private TableColumn<Customer, String> modifyContact_customer_name;
    @FXML
    private TableColumn<Customer, String> modifyContact_customer_address;
    @FXML
    private TableColumn<Customer, String> modifyContact_customer_postal_code;
    @FXML
    private TableColumn<Customer, String> modifyContact_customer_phone;
    @FXML
    private TableColumn<Customer, String> modifyContact_customer_FLD;

    /** This is the initialize method*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyCustomer_tbl.setItems(Planner.getAllCustomers());
        modifyContact_customer_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyContact_customer_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        modifyContact_customer_postal_code.setCellValueFactory(new PropertyValueFactory<>("zip"));
        modifyContact_customer_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        modifyContact_customer_FLD.setCellValueFactory(new PropertyValueFactory<>("division_name"));

        CountryQuery.select();

        // set countries for country combobox
        for (Country country : Planner.getAllCountries()) {
            countryList.add(country.getName());
        }

        modifyCustomer_country_field.setItems(countryList);

        // disable all fields on entry
        modifyCustomer_name_field.setDisable(true);
        modifyCustomer_address_field.setDisable(true);
        modifyCustomer_zip_field.setDisable(true);
        modifyCustomer_phone_field.setDisable(true);
        modifyCustomer_country_field.setDisable(true);
        modifyCustomer_FLD_field.setDisable(true);
        modifyCustomer_save_btn.setDisable(true);

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

    /** This is the event handler for the edit customer button
     * Fills fields with values of selected customer
     * */
    public void onAction_edit_customer(ActionEvent event) {

        selectedRow = modifyCustomer_tbl.getSelectionModel().getSelectedItem();

        if (selectedRow != null) { // if row selected

            // enable all fields
            modifyCustomer_name_field.setDisable(false);
            modifyCustomer_address_field.setDisable(false);
            modifyCustomer_zip_field.setDisable(false);
            modifyCustomer_phone_field.setDisable(false);
            modifyCustomer_country_field.setDisable(false);
            modifyCustomer_FLD_field.setDisable(false);
            modifyCustomer_save_btn.setDisable(false);

            // set values for fields
            modifyCustomer_id_field.setText(String.valueOf(selectedRow.getId()));
            modifyCustomer_name_field.setText(selectedRow.getName());
            modifyCustomer_address_field.setText(selectedRow.getAddress());
            modifyCustomer_zip_field.setText(selectedRow.getZip());
            modifyCustomer_phone_field.setText(selectedRow.getPhone());

            int country_id = FirstLevelDivisionQuery.selectCountry_ID(selectedRow.getDivision_id());
            String country_name = CountryQuery.selectCountryName(country_id);
            String division_name = FirstLevelDivisionQuery.selectDivisionName(selectedRow.getDivision_id());

            modifyCustomer_country_field.setValue(country_name);
            modifyCustomer_FLD_field.setValue(division_name);
        }
    }

    /** This is the event handler for the Country select combobox
     * Sets the values of the first level division combobox based on selected country
     * */
    public void onAction_country_select(ActionEvent event) {
        divisionList.clear();

        int country_id = -1;

        modifyCustomer_FLD_field.setDisable(false); // enable first level division combo

        for (Country country : Planner.getAllCountries()) { // get country id
            if (country.getName().equals(modifyCustomer_country_field.getValue())) {
                country_id = country.getId();
            }
        }

        FirstLevelDivisionQuery.select(country_id); // get first level divisions for selected country

        for (FirstLevelDivision division : Planner.getAllDivisions()) {
            divisionList.add(division.getDivision());
        }

        modifyCustomer_FLD_field.setItems(divisionList); // set first level divisions
    }

    /** This is the event handler for the Add Customer button
     * Adds customer to the database
     * */
    public void onAction_addContact_add(ActionEvent event) throws SQLException{

        try {
            // check for empty fields
            if (modifyCustomer_name_field.getText().isEmpty() ||
                    modifyCustomer_address_field.getText().isEmpty() ||
                    modifyCustomer_zip_field.getText().isEmpty() ||
                    modifyCustomer_phone_field.getText().isEmpty() ||
                    modifyCustomer_country_field.getValue() == null ||
                    modifyCustomer_FLD_field.getValue() == null) {
                throw new EmptyFieldException();
            }

            // get values from all fields
            int customer_id = Integer.parseInt(modifyCustomer_id_field.getText());
            String customer_name = modifyCustomer_name_field.getText();
            String address = modifyCustomer_address_field.getText();
            String zip = modifyCustomer_zip_field.getText();
            String phone = modifyCustomer_phone_field.getText();
            ZonedDateTime create_date = CustomerQuery.selectCreateDate(customer_id);
            String created_by = CustomerQuery.selectCreatedBy(customer_id);
            ZonedDateTime last_update = LocalDateTime.now().atZone(ZoneId.of("UTC"));
            String last_updated_by = loginController.user.getUser_name();
            int division_id = FirstLevelDivisionQuery.selectDivisionId(modifyCustomer_FLD_field.getValue());

            String division_name = modifyCustomer_FLD_field.getValue();

            // created customer object
            CustomerQuery.update(customer_id, customer_name, address, zip, phone, create_date, created_by, last_update,
                    last_updated_by, division_id);

            // refresh customer table
            CustomerQuery.select("SELECT * FROM client_schedule.customers");

            modifyCustomer_error_msg.setText("Customer successfully updated.");
        }
        catch (EmptyFieldException e) {
            modifyCustomer_error_msg.setText("Customer not updated. All fields must be filled.");
        }
    }

    /** This is the event handler for the delete customer button
     * Removes selected customer from the database
     * */
    public void onAction_delete_customer(ActionEvent event) {
        try {
            Customer selectedRow = modifyCustomer_tbl.getSelectionModel().getSelectedItem();

            if (selectedRow == null) { // if nothing selected
                throw new mainController.NullDeleteException();
            }

            // alert confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting this customer will also delete " +
                    "all associated appointments. Continue?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) { // if  continue
                CustomerQuery.delete(selectedRow.getId());
                modifyCustomer_error_msg.setText("Customer has been successfully removed");
            }
            CustomerQuery.select("SELECT * FROM client_schedule.customers");
        }
        catch (mainController.NullDeleteException e) {
            modifyCustomer_error_msg.setText("Nothing selected. No customers removed.");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAction_clear(ActionEvent event) {
        modifyCustomer_name_field.setText("");
        modifyCustomer_address_field.setText("");
        modifyCustomer_phone_field.setText("");
        modifyCustomer_zip_field.setText("");
        modifyCustomer_country_field.valueProperty().set(null);
    }

    static class EmptyFieldException extends Exception {
        public EmptyFieldException() {}
    }
}
