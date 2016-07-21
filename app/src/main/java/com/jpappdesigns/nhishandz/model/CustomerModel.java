package com.jpappdesigns.nhishandz.model;

/**
 * Created by jonathan.perez on 7/20/16.
 */
public class CustomerModel {

    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String phoneNumber;
    private String relationshipToChilde;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRelationshipToChilde() {
        return relationshipToChilde;
    }

    public void setRelationshipToChilde(String relationshipToChilde) {
        this.relationshipToChilde = relationshipToChilde;
    }
}
