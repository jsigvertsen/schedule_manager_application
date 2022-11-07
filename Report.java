package sigvertsen.c195.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Month;

/** class Report.java*/
public class Report {
    private static final ObservableList<ReportMonthType> allMonthTypeRecords = FXCollections.observableArrayList();
    private static final ObservableList<ReportContactMonth> allContactMonthRecords = FXCollections.observableArrayList();

    /** This is the getMonthTypeRecords method for the Report class
     * This method will return an array of all MonthTypeRecords in the class
     * */
    public static ObservableList<ReportMonthType> getMonthTypeRecords() { return allMonthTypeRecords; }

    /** This is the getContactMonthRecords method for the Report class
     * This method will return an array of all ContactMonthRecords in the class
     * */
    public static ObservableList<ReportContactMonth> getContactMonthRecords() { return allContactMonthRecords; }

    /** This is the addMonthTypeRecord method for the Report class
     * This method will add a MonthTypeRecord to the MonthTypeRecords array in the Reports class
     * */
    public static void addMonthTypeRecord(ReportMonthType record) {
        allMonthTypeRecords.add(record);
    }

    /** This is the addContactMonthRecord method for the Report class
     * This method will add a ContactMonthRecord to the ContactMonthRecords array in the Reports class
     * */
    public static void addContactMonthRecord(ReportContactMonth record) {
        allContactMonthRecords.add(record);
    }

    /** This is the clearMonthTypeRecords method for the Report class
     * This method will clear all MonthTypeRecords from the MonthTypeRecords array in the Reports class
     * */
    public static void clearMonthTypeRecords() { allMonthTypeRecords.clear(); }

    /** This is the clearContactMonthRecords method for the Report class
     * This method will clear all ContactMonthRecords from the ContactMonthRecords array in the Reports class
     * */
    public static void clearContactMonthRecords() { allContactMonthRecords.clear(); }

}
