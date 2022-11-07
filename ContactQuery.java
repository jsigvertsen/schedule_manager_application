package sigvertsen.c195.model;

import sigvertsen.c195.helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

/** class ContactQuery.java*/
public class ContactQuery {

    /** This is the select method for the ContactQuery class
     * This method will select contacts and create a contact object for each
     * */
    public static void select(String query) {

        try {
            Planner.clearContacts();

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(query);

            while (resultSet.next()) {

                Contact newContact = null;

                int id = resultSet.getInt("Contact_ID");
                String contact_name = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                newContact = new Contact(id, contact_name, email);

                Planner.addContact(newContact);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectContactName method for the ContactQuery class
     * This method will select a contact name given an id
     * */
    public static String selectContactName(int id) {

        try {
            String sql = "SELECT Contact_Name FROM client_schedule.contacts WHERE Contact_ID =" + id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getString("Contact_Name");
            }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
