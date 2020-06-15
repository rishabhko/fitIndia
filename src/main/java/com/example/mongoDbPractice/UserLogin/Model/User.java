package com.example.mongoDbPractice.UserLogin.Model;

import com.example.mongoDbPractice.common.validation.annotation.*;

import javax.validation.constraints.Email;

public class User {

    @UserID
    private Long id;
    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @Mail
    private String emailId;
    private String gender;
    @PhoneNumber
    private String phoneNumber;


    public User() {
    }

    public User(Long id, String firstName, String lastName, String emailId, String gender, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
