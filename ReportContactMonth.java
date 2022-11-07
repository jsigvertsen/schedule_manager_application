package sigvertsen.c195.model;

import java.time.Month;

/** class ReportContactMonth.java*/
public class ReportContactMonth {
    private String contact;
    private Month month;
    private int num_appts;

    public ReportContactMonth(String contact, Month month, int num_appts) {
        this.contact = contact;
        this.num_appts = num_appts;
        this.month = month;
    }

    /**
     * a method to get the contact of a ReportContactMonth Object
     * @return contact is the contact to get
     */
    public String getContact() {
        return contact;
    }

    /**
     * a method to set the contact to a ReportContactMonth object
     * @param contact is the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * a method to get the number of appointments of a ReportContactMonth Object
     * @return num_appts is the number of appointments to get
     */
    public int getNum_appts() {
        return num_appts;
    }

    /**
     * a method to set the number of appointments to a ReportContactMonth object
     * @param num_appts is the number of appointments to set
     */
    public void setNum_appts(int num_appts) {
        this.num_appts = num_appts;
    }

    /**
     * a method to get the month of a ReportContactMonth Object
     * @return month is the month to get
     */
    public Month getMonth() {
        return month;
    }

    /**
     * a method to set the month to a ReportContactMonth object
     * @param month is the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }
}
