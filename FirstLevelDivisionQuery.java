package sigvertsen.c195.model;

import sigvertsen.c195.helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

/** class FirstLevelDivisionQuery.java*/
public class FirstLevelDivisionQuery {

    /** This is the select method for the FirstLevelDivisionQuery class
     * This method will select first level divisions, create objects, and add them to the planner class
     * */
    public static void select(int country_id) {
        try {

            Planner.clearDivisions();

            String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID = " + String.valueOf(country_id);

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {

                FirstLevelDivision newDivision = null;

                int id = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");

                newDivision = new FirstLevelDivision(id, division);

                Planner.addDivision(newDivision);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectDivisionName query for the FirstLevelDivisionQuery class
     * This method will select division name from the database given a division id
     * */
    public static String selectDivisionName(int division_id) {
        try {

            String sql = "SELECT Division FROM client_schedule.first_level_divisions WHERE Division_ID = " + division_id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getString("Division");

            }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectDivisionId method for the FirstLevelDivisionQuery class
     * This method will select a division id from the database given a division name
     * */
    public static int selectDivisionId(String division_name) {
        try {

            String sql = "SELECT Division_ID FROM client_schedule.first_level_divisions WHERE Division = '" + division_name + "'";

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getInt("Division_ID");

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectCountry_ID method for the FirstLevelDivisionQuery class
     * This method will select a country id from the database given a division id
     * */
    public static int selectCountry_ID(int division_id) {
        try {

            String sql = "SELECT Country_ID FROM client_schedule.first_level_divisions WHERE Division_ID = " + division_id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getInt("Country_ID");

            }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
