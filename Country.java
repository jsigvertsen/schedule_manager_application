package sigvertsen.c195.model;

/** class Country.java*/
public class Country {
    public int id;
    public String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * a method to get the country id of a Country Object
     * @return id is the country id to get
     */
    public int getId() {
        return id;
    }

    /**
     * a method to set the country id to a Country object
     * @param id is the country id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * a method to get the country name of a Country Object
     * @return name is the country name to get
     */
    public String getName() {
        return name;
    }

    /**
     * a method to set the country name to a Country object
     * @param name is the country name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
