package sigvertsen.c195.model;

import java.time.Month;

/** class ReportMonthType.java*/
public class ReportMonthType {

    private String type;
    private Month month;
    private int type_count;

    public ReportMonthType(String type, Month month, int type_count) {
        this.type = type;
        this.month = month;
        this.type_count = type_count;
    }

    /**
     * a method to get the type of a ReportMonthType Object
     * @return type is the type to get
     */
    public String getType() {
        return type;
    }

    /**
     * a method to set the type to a ReportMonthType object
     * @param type is the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * a method to get the month of a ReportMonthType Object
     * @return month is the month to get
     */
    public Month getMonth() {
        return month;
    }

    /**
     * a method to set the month to a ReportMonthType object
     * @param month is the month to set
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * a method to get the type count of a ReportMonthType Object
     * @return type_count is the type count to get
     */
    public int getType_count() {
        return type_count;
    }

    /**
     * a method to set the type count to a ReportMonthType object
     * @param type_count is the type count to set
     */
    public void setType_count(int type_count) {
        this.type_count = type_count;
    }
}
