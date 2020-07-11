package com.example.mongoDbPractice.UserLogin.Model;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.common.validation.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "UserData")
public class User {

    //    @UserID
    @Id
    private String id;
    private String password;
    private int uin;

    private String DOB;
    @FirstName
    private String firstName;
    @LastName
    private String lastName;

    private String gender;
    @PhoneNumber
    private String phoneNumber;

    private Boolean googleVerified;
    private  Boolean fbVerified;

    private List<String> coursesEnrolledIds;


    public User() {
    }


    public User(String id, String password, int uin, String DOB, String firstName, String lastName, String gender, String phoneNumber, Boolean googleVerified, Boolean fbVerified, List<String> coursesEnrolledIds) {
        this.id = id;
        this.password = password;
        this.uin = uin;
        this.DOB = DOB;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.googleVerified = googleVerified;
        this.fbVerified = fbVerified;
        this.coursesEnrolledIds = coursesEnrolledIds;
    }

    public int getUin() {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public List<String> getCoursesEnrolledIds() {
        return coursesEnrolledIds;
    }

    public void setCoursesEnrolledIds(List<String> coursesEnrolledIds) {
        this.coursesEnrolledIds = coursesEnrolledIds;
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