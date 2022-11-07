package sigvertsen.c195.model;

import sigvertsen.c195.controller.loginController;
import sigvertsen.c195.helper.JDBC;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** class AppointmentQuery.java*/
public class AppointmentQuery {

    /** This is the update method for the AppointmentQuery class
     * This class updates Appointment objects in the database
     * */
    public static void update(String title, String desc, String location, String type, ZonedDateTime start,
                              ZonedDateTime end, String contact, String customer, int id) throws SQLException {

        int customer_id = -1;
        int contact_id = -1;

        String sql = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, " +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        // get customer name
        for (Customer i : Planner.getAllCustomers()) {
            if (i.getName().equals(customer)) {
                customer_id = i.getId();
            }
        }

        // get contact id
        for (Contact i : Planner.getAllContacts()) {
            if (i.getName().equals(contact)) {
                contact_id = i.getId();
            }
        }

        // convert to UTC
        LocalDateTime start_final = TimeZoneConversion.ConvertToZone(start.toLocalDateTime(), ZoneId.systemDefault(), ZoneId.of("UTC"));
        LocalDateTime end_final = TimeZoneConversion.ConvertToZone(end.toLocalDateTime(), ZoneId.systemDefault(), ZoneId.of("UTC"));
        LocalDateTime current = TimeZoneConversion.ConvertToZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"));

        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start_final));
        ps.setTimestamp(6, Timestamp.valueOf(end_final));
        ps.setTimestamp(7, Timestamp.valueOf(current));
        ps.setString(8, loginController.user.getUser_name());
        ps.setTimestamp(9, Timestamp.valueOf(current));
        ps.setString(10, loginController.user.getUser_name());
        ps.setInt(11, customer_id);
        ps.setInt(12, loginController.user.getUser_id());
        ps.setInt(13, contact_id);
        ps.setInt(14, id);

        ps.executeUpdate();
    }

    /** This is the delete method for the AppointmentQuery class
     * Deletes an appointment object from the database
     * */
    public static void delete(int Appointment_ID) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Appointment_ID);
        ps.executeUpdate();

    }

    /** This is the insert method for the AppointmentQuery class
     * Inserts an appointment object into the database
     * */
    public static void insert(String title, String desc, String location, String type, ZonedDateTime start,
                              ZonedDateTime end, String contact, String customer) throws SQLException {

        int customer_id = -1;
        int contact_id = -1;

        String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        // get customer id
        for (Customer i : Planner.getAllCustomers()) {
            if (i.getName().equals(customer)) {
                customer_id = i.getId();
            }
        }

        // get contact id
        for (Contact i : Planner.getAllContacts()) {
            if (i.getName().equals(contact)) {
                contact_id = i.getId();
            }
        }

        // convert to utc
        LocalDateTime start_final = TimeZoneConversion.ConvertToZone(start.toLocalDateTime(), ZoneId.systemDefault(), ZoneId.of("UTC"));
        LocalDateTime end_final = TimeZoneConversion.ConvertToZone(end.toLocalDateTime(), ZoneId.systemDefault(), ZoneId.of("UTC"));
        LocalDateTime current = TimeZoneConversion.ConvertToZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"));

        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start_final));
        ps.setTimestamp(6, Timestamp.valueOf(end_final));
        ps.setTimestamp(7, Timestamp.valueOf(current));
        ps.setString(8, loginController.user.getUser_name());
        ps.setTimestamp(9, Timestamp.valueOf(current));
        ps.setString(10, loginController.user.getUser_name());
        ps.setInt(11, customer_id);
        ps.setInt(12, loginController.user.getUser_id());
        ps.setInt(13, contact_id);

        ps.executeUpdate();
    }

    /** This is the select method for the AppointmentQuery class
     * selects and creates appointment objects from the database
     * */
    public static void select(String query) {
        try {
            Planner.clearAppointments();

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(query);

            while (resultSet.next()) {

                Appointment newAppt = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String desc = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start_time = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end_time = resultSet.getTimestamp("End").toLocalDateTime();
                LocalDateTime create_date = resultSet.getTimestamp("Create_Date").toLocalDateTime();
                String created_by = resultSet.getString("Created_By");
                LocalDateTime last_update = resultSet.getTimestamp("Last_Update").toLocalDateTime();
                String last_updated_by = resultSet.getString("Last_Updated_By");
                int customer_id = resultSet.getInt("Customer_ID");
                int user_id = resultSet.getInt("User_ID");
                int contact_id = resultSet.getInt("Contact_ID");

                // get contact name
                String contact_name = ContactQuery.selectContactName(contact_id);

                // covert all times to user system time zone
                start_time = TimeZoneConversion.ConvertToZone(start_time, ZoneId.of("UTC"), ZoneId.systemDefault());
                end_time = TimeZoneConversion.ConvertToZone(end_time, ZoneId.of("UTC"), ZoneId.systemDefault());
                create_date = TimeZoneConversion.ConvertToZone(create_date, ZoneId.of("UTC"), ZoneId.systemDefault());
                last_update = TimeZoneConversion.ConvertToZone(last_update, ZoneId.of("UTC"), ZoneId.systemDefault());

                newAppt = new Appointment(id, title, desc, location, type, start_time, end_time, create_date, created_by,
                        last_update, last_updated_by, customer_id, user_id, contact_id, contact_name);

                // add to planner list
                Planner.addAppointment(newAppt);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
            }
    }

    /** This is the selectAppointmentType method for the AppointmentQuery class
     * Selects distinct appointment types from the database
     * */
    public static void selectAppointmentByType() {
        try {
            Planner.clearAppointmentsByType();

            String sql = "SELECT DISTINCT Type FROM client_schedule.appointments;";

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {

                String type = resultSet.getString("Type");

                Planner.addAppointmentType(type); // add to planner type list
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the reportMonthAndType method for the AppointmentQuery class
     * Selects distinct type, month of start date, and count of different types for appointments
     * */
    public static void reportMonthAndType(String month) {
        try {

            Month month_name  = Month.valueOf(month);

            int month_num = month_name.getValue(); // get month number of month

            String sql = "SELECT DISTINCT(type) as 'Type', month(Start) as 'Month', COUNT(type) as 'Number of Appointments' FROM client_schedule.appointments WHERE month(Start) = " + month_num + " GROUP BY type, month(Start);";

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {

                String type1 = resultSet.getString("Type");
                Month month1 = Month.of(resultSet.getInt("Month"));
                int type_count = resultSet.getInt("Number of Appointments");

                ReportMonthType record = new ReportMonthType(type1, month1, type_count);

                Report.addMonthTypeRecord(record); // add to report class
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the reportContactAndMonth for the AppointmentQuery class
     * Selects distinct contact_id, month of start date, and count of distinct contact_id's from database
     * */
    public static void reportContactAndMonth(String month) {
        try {

            Month month_name  = Month.valueOf(month);

            int month_num = month_name.getValue(); // get month number of month

            String sql = "SELECT DISTINCT(Contact_ID), month(Start) as 'Month', count(Contact_ID) as 'Number of Appointments' FROM client_schedule.appointments WHERE Month(Start) = " + month_num + " GROUP BY Month, Contact_ID, 'Number of Appointments';";

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {

                int contact_id = resultSet.getInt("Contact_ID");
                Month month1 = Month.of(resultSet.getInt("Month"));
                int num_appts = resultSet.getInt("Number of Appointments");

                String contact_name = ContactQuery.selectContactName(contact_id); // get contact name

                ReportContactMonth record = new ReportContactMonth(contact_name, month1, num_appts);

                Report.addContactMonthRecord(record); // add to report class
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
