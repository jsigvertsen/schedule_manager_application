package sigvertsen.c195.model;

/** class FirstLevelDivision.java*/
public class FirstLevelDivision {

    private int id;
    private String division;

    public FirstLevelDivision(int id, String division) {
        this.id = id;
        this.division = division;
    }

    /**
     * a method to get the first level division id of a FirstLevelDivision Object
     * @return id is the first level division id to get
     */
    public int getId() {
        return id;
    }

    /**
     * a method to set the first level division id to a FirstLevelDivision object
     * @param id is the first level division id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * a method to get the first level division of a FirstLevelDivision Object
     * @return division is the first level division to get
     */
    public String getDivision() {
        return division;
    }

    /**
     * a method to set the first level division to a FirstLevelDivision object
     * @param division is the first level division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }
}
