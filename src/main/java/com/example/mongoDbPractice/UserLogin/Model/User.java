package com.example.mongoDbPractice.UserLogin.Model;

import com.example.mongoDbPractice.common.validation.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


@Document(collection = "UserData")
@Component
public class User {

    //    @UserID
    @Id
    private String id;
    private String password;
    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @Mail
    private String emailId;
    private String gender;
    @PhoneNumber
    private String phoneNumber;

    private Boolean googleVerified;
    private  Boolean fbVerified;


    public User() {
    }

    public User(String id, String password, String firstName, String lastName, String emailId, String gender, String phoneNumber, Boolean googleVerified, Boolean fbVerified) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.googleVerified = googleVerified;
        this.fbVerified = fbVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getGoogleVerified() {
        return googleVerified;
    }

    public void setGoogleVerified(Boolean googleVerified) {
        this.googleVerified = googleVerified;
    }

    public Boolean getFbVerified() {
        return fbVerified;
    }

    public void setFbVerified(Boolean fbVerified) {
        this.fbVerified = fbVerified;
    }
}