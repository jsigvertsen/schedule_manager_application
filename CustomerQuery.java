package sigvertsen.c195.model;

import sigvertsen.c195.controller.loginController;
import sigvertsen.c195.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** class CustomerQuery.java*/
public class CustomerQuery {

    /** This is the delete method of the CustomerQuery class
     * This method will delete a customer from the database given a customer id
     * */
    public static void delete(int customer_id) throws SQLException {

        String sql = "DELETE FROM client_schedule.appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer_id);
        ps.executeUpdate();

        sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer_id);
        ps.executeUpdate();

    }

    /** This is the update method of the CustomerQuery class
     * This method will update a customer object in the database
     * */
    public static void update(int customer_id, String customer_name, String address, String zip, String phone,
                              ZonedDateTime create_date, String created_by, ZonedDateTime last_update,
                              String last_updated_by, int division_id) throws SQLException {

        String sql = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        LocalDateTime current = TimeZoneConversion.ConvertToZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"));

        ps.setString(1, customer_name);
        ps.setString(2, address);
        ps.setString(3, zip);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(create_date.toLocalDateTime()));
        ps.setString(6, created_by);
        ps.setTimestamp(7, Timestamp.valueOf(current));
        ps.setString(8, last_updated_by);
        ps.setInt(9, division_id);
        ps.setInt(10, customer_id);

        ps.executeUpdate();
    }

    /** This is the insert method of the CustomerQuery class
     * This method will insert a customer object in the database
     * */
    public static void insert(String name, String address, String zip, String phone,
                              String division_name) throws SQLException {

        int division_id = -1;

        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        for (FirstLevelDivision division : Planner.getAllDivisions()) {
            if (division.getDivision().equals(division_name)) {
                division_id = division.getId();
            }
        }

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, zip);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
        ps.setString(6, loginController.user.getUser_name());
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
        ps.setString(8, loginController.user.getUser_name());
        ps.setInt(9, division_id);

        ps.executeUpdate();
    }

    /** This is the select method of the CustomerQuery class
     * This method will select customers from the database, create customer objects, and add them to the planner class
     * */
    public static void select(String query) {

        try {
            Planner.clearCustomers();

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(query);

            while (resultSet.next()) {

                Customer newCust = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                int id = resultSet.getInt("Customer_ID");
                String customer_name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postal_code = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                LocalDateTime create_date = LocalDateTime.parse(resultSet.getString("Create_Date"), formatter);
                String created_by = resultSet.getString("Created_By");
                LocalDateTime last_update = LocalDateTime.parse(resultSet.getString("Last_Update"), formatter);
                String last_updated_by = resultSet.getString("Last_Updated_By");
                int division_id = resultSet.getInt("Division_ID");

                String division_name = FirstLevelDivisionQuery.selectDivisionName(division_id);

                newCust = new Customer(id, customer_name, address, postal_code, phone, create_date, created_by, last_update,
                        last_updated_by, division_id, division_name);

                Planner.addCustomer(newCust);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectCustomerName method of the CustomerQuery class
     * This method will select a customer name given a customer id
     * */
    public static String selectCustomerName(int id) {

        try {
            String sql = "SELECT Customer_Name FROM client_schedule.customers WHERE Customer_ID =" + id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getString("Customer_Name");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectCustomerId method of the CustomerQuery class
     * This method will select a customer id given a customer name
     * */
    public static int selectCustomerId(String customer_name) {

        try {
            String sql = "SELECT Customer_ID FROM client_schedule.customers WHERE Customer_Name = '" + customer_name + "'";

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getInt("Customer_ID");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectCreateDate method of the CustomerQuery class
     * This method will select a customer create date given a customer id
     * */
    public static ZonedDateTime selectCreateDate(int customer_id) {
        try {
            String sql = "SELECT Create_Date FROM client_schedule.customers WHERE Customer_ID = " + customer_id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();

            LocalDateTime time = resultSet.getTimestamp("Create_Date").toLocalDateTime();

            return ZonedDateTime.of(time, ZoneId.systemDefault());

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectCreateBy method of the CustomerQuery class
     * This method will select created by given a customer id
     * */
    public static String selectCreatedBy(int customer_id) {
        try {
            String sql = "SELECT Created_By FROM client_schedule.customers WHERE Customer_ID = " + customer_id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();

            return resultSet.getString("Created_By");

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
