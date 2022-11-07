package sigvertsen.c195.model;

import sigvertsen.c195.helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

/** class CountryQuery.java*/
public class CountryQuery {

    /** This is the select method for the CountryQuery class
     * This method will select countries, create a Country object for each, and add to the planner class
     * */
    public static void select() {
        try {

            Planner.clearCountries();

            String sql = "SELECT * FROM client_schedule.countries";

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);

            while (resultSet.next()) {

                Country newCountry = null;

                int id = resultSet.getInt("Country_ID");
                String name = resultSet.getString("Country");

                newCountry = new Country(id, name);

                Planner.addCountry(newCountry);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** This is the selectCountryName method for the CountryQuery class
     * This method will select a country name given a country id
     * */
    public static String selectCountryName(int country_id) {
        try {

            String sql = "SELECT Country FROM client_schedule.countries WHERE Country_ID =" + country_id;

            ResultSet resultSet = JDBC.connection.createStatement().executeQuery(sql);
            resultSet.next();
            return resultSet.getString("Country");

            }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
