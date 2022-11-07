package sigvertsen.c195.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;

/** class Planner.java*/
public class Planner {
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();

    private static final ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    private static final ObservableList<String> appointmentsByType = FXCollections.observableArrayList();

    private static final ObservableList<String> timeList = FXCollections.observableArrayList();

    private static final ObservableList<String> monthList = FXCollections.observableArrayList();

    private static String user;

    /**
     * a method to set the user_name after log in
     * @param user_name is the user_name to set
     */
    public static void setUser(String user_name) {
        user = user_name;
    }

    /**
     * a method to get the user after log in
     * @return user is the user to get
     */
    public static String getUser() {
        return user;
    }

    /**
     * a method to get the get all appointments from the Planner class
     * @return allAppointments is the array of all appointments to get
     */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     * a method to get the get all customers from the Planner class
     * @return allCustomers is the array of all customers to get
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * a method to get the get all contacts from the Planner class
     * @return allContacts is the array of all contacts to get
     */
    public static ObservableList<Contact> getAllContacts() { return allContacts; }

    /**
     * a method to get the get all countries from the Planner class
     * @return allCountries is the array of all countries to get
     */
    public static ObservableList<Country> getAllCountries() { return allCountries; }

    /**
     * a method to get the get all divisions from the Planner class
     * @return allDivisions is the array of all divisions to get
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() { return allDivisions; }

    /**
     * a method to get the get a list of times
     * @return timeList is the array of all times to get
     */
    public static ObservableList<String> getTimeList() {

        timeList.clear();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");



        for (int i = 8; i < 22; i++) {

            // convert to system default time zone
            LocalDateTime time1 =  TimeZoneConversion.ConvertToZone(LocalDateTime.of(LocalDate.now(), LocalTime.of(i, 0)), ZoneId.of("US/Eastern"), ZoneId.systemDefault());
            LocalDateTime time2 =  TimeZoneConversion.ConvertToZone(LocalDateTime.of(LocalDate.now(), LocalTime.of(i, 15)), ZoneId.of("US/Eastern"), ZoneId.systemDefault());
            LocalDateTime time3 =  TimeZoneConversion.ConvertToZone(LocalDateTime.of(LocalDate.now(), LocalTime.of(i, 30)), ZoneId.of("US/Eastern"), ZoneId.systemDefault());
            LocalDateTime time4 =  TimeZoneConversion.ConvertToZone(LocalDateTime.of(LocalDate.now(), LocalTime.of(i, 45)), ZoneId.of("US/Eastern"), ZoneId.systemDefault());

            // add to timeList
            timeList.add(time1.toLocalTime().format(format));
            timeList.add(time2.toLocalTime().format(format));
            timeList.add(time3.toLocalTime().format(format));
            timeList.add(time4.toLocalTime().format(format));
        }
        // add final time
        LocalDateTime time5 =  TimeZoneConversion.ConvertToZone(LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0)), ZoneId.of("US/Eastern"), ZoneId.systemDefault());
        timeList.add(time5.toLocalTime().format(format));
        return timeList;
    }

    /**
     * a method to get the get all months
     * @return monthList is the array of all months to get
     */
    public static ObservableList<String> getMonthList() {

        clearMonthList();

        // add all months of month type to monthList
        monthList.add(String.valueOf(Month.JANUARY));
        monthList.add(String.valueOf(Month.FEBRUARY));
        monthList.add(String.valueOf(Month.MARCH));
        monthList.add(String.valueOf(Month.APRIL));
        monthList.add(String.valueOf(Month.MAY));
        monthList.add(String.valueOf(Month.JUNE));
        monthList.add(String.valueOf(Month.JULY));
        monthList.add(String.valueOf(Month.AUGUST));
        monthList.add(String.valueOf(Month.SEPTEMBER));
        monthList.add(String.valueOf(Month.OCTOBER));
        monthList.add(String.valueOf(Month.NOVEMBER));
        monthList.add(String.valueOf(Month.DECEMBER));

        return monthList;
    }

    /** This is the addAppointment method for the Planner class
     * This method will add an appointment to the appointment array in the Planner class
     * */
    public static void addAppointment(Appointment appt) {
        allAppointments.add(appt);
    }

    /** This is the addCustomer method for the Planner class
     * This method will add a customer to the customer array in the Planner class
     * */
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /** This is the addContact method for the Planner class
     * This method will add a contact to the contact array in the Planner class
     * */
    public static void addContact(Contact contact) { allContacts.add(contact); }

    /** This is the addCountry method for the Planner class
     * This method will add a country to the country array in the Planner class
     * */
    public static void addCountry(Country country) { allCountries.add(country); }

    /** This is the addDivision method for the Planner class
     * This method will add a division to the division array in the Planner class
     * */
    public static void addDivision(FirstLevelDivision division) { allDivisions.add(division); }

    /** This is the addAppointmentType method for the Planner class
     * This method will add a appointment type to the appointment type array in the Planner class
     * */
    public static void addAppointmentType(String type) { appointmentsByType.add(type); }

    /** This is the addMonths method for the Planner class
     * This method will add a month to the monthList array in the Planner class
     * */
    public static void addMonths(String month) {
        monthList.add(month);
    }

    /** This is the clearAppointments method for the Planner class
     * This method will clear all appointments from the appointments array in the Planner class
     * */
    public static void clearAppointments() {
        allAppointments.clear();
    }

    /** This is the clearCustomer method for the Planner class
     * This method will clear all customers from the customers array in the Planner class
     * */
    public static void clearCustomers() {
        allCustomers.clear();
    }

    /** This is the clearContacts method for the Planner class
     * This method will clear all contacts from the contacts array in the Planner class
     * */
    public static void clearContacts() {
        allContacts.clear();
    }

    /** This is the clearDivisions method for the Planner class
     * This method will clear all divisions from the divisions array in the Planner class
     * */
    public static void clearDivisions() {
        allDivisions.clear();
    }

    /** This is the clearCountries method for the Planner class
     * This method will clear all countries from the countries array in the Planner class
     * */
    public static void clearCountries() {
        allCountries.clear();
    }

    /** This is the clearAppointmentByType method for the Planner class
     * This method will clear all appointments types from the appointmentsByType array in the Planner class
     * */
    public static void clearAppointmentsByType() { appointmentsByType.clear(); }

    /** This is the clearMonthList method for the Planner class
     * This method will clear all months from the months array in the Planner class
     * */
    public static void clearMonthList() { monthList.clear(); }

    /** This is the lookupCustomer method for the Planner class
     * This method will return a customer containing the id or name in the input
     * */
    public static ObservableList<Customer> lookupCustomer(String input) {

        ObservableList<Customer> tempCustomerList = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            // if contains input
            if (customer.getName().toLowerCase().contains(input) || String.valueOf(customer.getId()).contains(input)) {
                tempCustomerList.add(customer);
            }
        }
        return tempCustomerList;
    }
}
