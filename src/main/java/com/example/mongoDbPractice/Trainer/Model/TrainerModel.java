package com.example.mongoDbPractice.Trainer.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Trainer")
public class TrainerModel {


    String name;
    @Id
    String id;
    Integer uin;
    String password;
    String DOB;
    String gender;
    String profilePicPath;
    Boolean verified;
    String about;
    String[] certificatePaths;
    List<Course> courses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String[] getCertificatePaths() {
        return certificatePaths;
    }

    public void setCertificatePaths(String[] certificatePaths) {
        this.certificatePaths = certificatePaths;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Integer getUin() {
        return uin;
    }

    public void setUin(Integer uin) {
        this.uin = uin;
    }

    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public TrainerModel() {
    }

    public TrainerModel(String name, String id, Integer uin, String password, String DOB, String gender, String profilePicPath, Boolean verified, String about, String[] certificatePaths, List<Course> courses) {
        this.name = name;
        this.id = id;
        this.uin = uin;
        this.password = password;
        this.DOB = DOB;
        this.gender = gender;
        this.profilePicPath = profilePicPath;
        this.verified = verified;
        this.about = about;
        this.certificatePaths = certificatePaths;
        this.courses = courses;
    }
}
