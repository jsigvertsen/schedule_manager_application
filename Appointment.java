package sigvertsen.c195.model;

import java.time.LocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;

/** class Appointment.java*/
public class Appointment {
    private int id;
    private String title;
    private String desc;
    private String location;
    private String type;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private LocalDateTime create_date;
    private String created_by;
    private LocalDateTime last_update;
    private String last_updated_by;
    private int customer_id;
    private int user_id;
    private int contact_id;
    private String contact_name;

    public Appointment(int id, String title, String desc, String location, String type, LocalDateTime start_time,
                       LocalDateTime end_time, LocalDateTime create_date, String created_by, LocalDateTime last_update,
                       String last_updated_by, int customer_id, int user_id, int contact_id, String contact_name) {

        this.id = id;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
        this.contact_name = contact_name;
    }

    /**
     * a method to get the appointment id of an Appointment Object
     * @return id is the Appointment Id to get
     */
    public int getId() {
        return id;
    }

    /**
     * a method to set the appointment id to an Appointment object
     * @param id is the appointment id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * a method to get the appointment title of an Appointment Object
     * @return title is the appointment title to get
     */
    public String getTitle() {
        return title;
    }

    /**
     * a method to set the appointment id to an Appointment object
     * @param title is the appointment title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * a method to get the appointment id of an Appointment Object
     * @return desc is the appointment description to get
     */
    public String getDesc() {
        return desc;
    }

    /**
     * a method to set the appointment desc to an Appointment object
     * @param desc is the appointment desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * a method to get the appointment location of an Appointment Object
     * @return location is the appointment location to get
     */
    public String getLocation() {
        return location;
    }

    /**
     * a method to set the appointment location to an Appointment object
     * @param location is the appointment location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * a method to get the appointment type of an Appointment Object
     * @return type is the appointment type to get
     */
    public String getType() {
        return type;
    }

    /**
     * a method to set the appointment type to an Appointment object
     * @param type is the appointment type to get
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * a method to get the appointment start time of an Appointment Object
     * @return start_time is the appointment start time to set
     */
    public LocalDateTime getStart_time() {
        return start_time;
    }

    /**
     * a method to get the appointment id of an Appointment Object
     * @return id is the Company Name to get
     */
    public String getStart_time_formatted() {
        return start_time.format(DateTimeFormatter.ofPattern("hh:mm a MM/dd/yy"));
    }

    /**
     * a method to set the appointment start time to an Appointment object
     * @param start_time is the appointment start time to set
     */
    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    /**
     * a method to get the appointment end time of an Appointment Object
     * @return end_time is the appointment end time to get
     */
    public LocalDateTime getEnd_time() {
        return end_time;
    }

    /**
     * a method to get the appointment end time formatted of an Appointment Object
     * @return end_time_formatted is the appointment end time formatted to get
     */
    public String getEnd_time_formatted() {
        return end_time.format(DateTimeFormatter.ofPattern("hh:mm a MM/dd/yy"));
    }

    /**
     * a method to set the appointment end time to an Appointment object
     * @param end_time is the appointment end time to set
     */
    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    /**
     * a method to get the appointment create date of an Appointment Object
     * @return create_date is the appointment create date to get
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    /**
     * a method to set the appointment create date to an Appointment object
     * @param create_date is the appointment create date to set
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    /**
     * a method to get the appointment created by of an Appointment Object
     * @return created_by is the appointment created by to get
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * a method to set the appointment created by to an Appointment object
     * @param created_by is the appointment created by to set
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * a method to get the appointment updated by of an Appointment Object
     * @return last_updated_by is the appointment updated by to get
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * a method to set the appointment last updated by to an Appointment object
     * @param last_updated_by is the appointment last updated by to set
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * a method to set the customer id to an Appointment object
     * @param customer_id is the customer id to set
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * a method to get the customer id of an Appointment Object
     * @return customer_id is the customer id to get
     */
    public int getCustomer_id() { return customer_id; }

    public int getUser_id() {
        return user_id;
    }

    /**
     * a method to set the appointment user id to an Appointment object
     * @param user_id is the appointment user id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * a method to get the appointment contact id of an Appointment Object
     * @return contact_id is the appointment contact id to get
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * a method to set the appointment contact id to an Appointment object
     * @param contact_id is the appointment contact id to set
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * a method to get the appointment contact name of an Appointment Object
     * @return id is the appointment contact name to get
     */
    public String getContact_name() {
        return contact_name;
    }

    /**
     * a method to set the appointment contact name to an Appointment object
     * @param contact_name is the appointment contact name to set
     */
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}
