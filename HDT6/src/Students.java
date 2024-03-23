

/**
 * The Students class represents a student with their name, phone number, email, postal/zip code, and country.
 */
public class Students {
    private String name;
    private String phone;
    private String email;
    private String postalZip;
    private String country;

    /**
     * Constructs a new Students object with the specified name, phone number, email, postal/zip code, and country.
     *
     * @param name       the name of the student
     * @param phone      the phone number of the student
     * @param email      the email address of the student
     * @param postalZip  the postal or zip code of the student
     * @param country    the country of the student
     */
    public Students(String name, String phone, String email, String postalZip, String country) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.postalZip = postalZip;
        this.country = country;
    }

    /**
     * Returns the name of the student.
     *
     * @return the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number of the student.
     *
     * @return the phone number of the student
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the email address of the student.
     *
     * @return the email address of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the postal or zip code of the student.
     *
     * @return the postal or zip code of the student
     */
    public String getPostalZip() {
        return postalZip;
    }

    /**
     * Returns the country of the student.
     *
     * @return the country of the student
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns a string representation of the Students object.
     *
     * @return a string representation of the Students object
     */
    public String toString() {
        return "Name: " + name + "\nPhone: " + phone + "\nEmail: " + email + "\nPostal/Zip: " + postalZip + "\nCountry: " + country;
    }
}
