package com.example.client.model;



import java.util.Date;


public class Artist {

    private Long id;
    private String firstName;
    private String lastName;

    private String otherName;

    private Date dateOfBirth;


    public Artist() {
    }
    /**
     * Parameterized constructor.
     *
     * @param firstName the first name of the artist
     * @param lastName the last name of the artist
     * @param otherName the other name of the artist (optional)
     * @param dateOfBirth the date of birth of the artist (optional)
     */
    public Artist(Long id, String firstName, String lastName, String otherName, Date dateOfBirth) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return " " + firstName + ' ' + " " + lastName + ' ' +" " + otherName + ' ' ;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

