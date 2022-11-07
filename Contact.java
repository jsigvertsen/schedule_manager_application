package sigvertsen.c195.model;

/** class Contact.java*/
public class Contact {
    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * a method to get the contact id of a contact Object
     * @return id is the contact Id to get
     */
    public int getId() {
        return id;
    }

    /**
     * a method to set the contact id to a contact object
     * @param id is the contact id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * a method to get the contact name of a contact Object
     * @return name is the contact name to get
     */
    public String getName() {
        return name;
    }

    /**
     * a method to set the contact name to a contact object
     * @param name is the contact name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * a method to get the contact email of a contact Object
     * @return email is the contact email to get
     */
    public String getEmail() {
        return email;
    }

    /**
     * a method to set the contact email to a contact object
     * @param email is the contact email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }


}
