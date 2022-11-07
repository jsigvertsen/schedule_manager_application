package sigvertsen.c195.model;

import java.time.LocalDateTime;

/** class Customer.java*/
public class Customer {

    private int id;
    private String name;
    private String address;
    private String zip;
    private String phone;
    private LocalDateTime create_date;
    private String created_by;
    private LocalDateTime last_update;
    private String last_updated_by;
    private int division_id;
    private String division_name;

    public Customer(int id, String name, String address, String zip, String phone, LocalDateTime create_date,
                    String created_by, LocalDateTime last_update, String last_updated_by, int division_id, String division_name) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.create_date = create_date;
        this.created_by = created_by;
        this.last_update = last_update;
        this.last_updated_by = last_updated_by;
        this.division_id = division_id;
        this.division_name = division_name;
    }

    /**
     * a method to get the customer id of a Customer Object
     * @return id is the customer id to get
     */
    public int getId() {
        return id;
    }

    /**
     * a method to set the customer id to a Customer object
     * @param id is the customer id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * a method to get the customer name of a Customer Object
     * @return id is the customer name to get
     */
    public String getName() {
        return name;
    }

    /**
     * a method to set the customer name to a Customer object
     * @param name is the customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * a method to get the customer address of a Customer Object
     * @return address is the customer address to get
     */
    public String getAddress() {
        return address;
    }

    /**
     * a method to set the customer address to a Customer object
     * @param address is the customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * a method to get the customer zip of a Customer Object
     * @return zip is the customer zip to get
     */
    public String getZip() {
        return zip;
    }

    /**
     * a method to set the customer zip to a Customer object
     * @param zip is the customer zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * a method to get the customer phone of a Customer Object
     * @return phone is the customer phone to get
     */
    public String getPhone() {
        return phone;
    }

    /**
     * a method to set the customer phone to a Customer object
     * @param phone is the customer phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * a method to get the customer create_date of a Customer Object
     * @return create_date is the customer create_date to get
     */
    public LocalDateTime getCreate_date() {
        return create_date;
    }

    /**
     * a method to set the customer create_date to a Customer object
     * @param create_date is the customer create_date to set
     */
    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    /**
     * a method to get the customer created_by of a Customer Object
     * @return created_by is the customer created_by to get
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * a method to set the customer created_by to a Customer object
     * @param created_by is the customer created_by to set
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    /**
     * a method to get the customer last_update of a Customer Object
     * @return last_update is the customer last_update to get
     */
    public LocalDateTime getLast_update() {
        return last_update;
    }

    /**
     * a method to set the customer last_update to a Customer object
     * @param last_update is the customer last_update to set
     */
    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }

    /**
     * a method to get the customer last_updated_by of a Customer Object
     * @return last_updated_by is the customer last_updated_by to get
     */
    public String getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * a method to set the customer last_udpated_by to a Customer object
     * @param last_updated_by is the customer last_updated_by to set
     */
    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    /**
     * a method to get the customer division_id of a Customer Object
     * @return division_id is the customer division_id to get
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * a method to set the customer division_id to a Customer object
     * @param division_id is the customer division_id to set
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    /**
     * a method to get the customer division_name of a Customer Object
     * @return division_name is the customer division_name to get
     */
    public String getDivision_name() {
        return division_name;
    }

    /**
     * a method to set the customer division_name to a Customer object
     * @param division_name is the customer division_name to set
     */
    public void setDivision_name(String division_name) {
        this.division_name = division_name;
    }
}
