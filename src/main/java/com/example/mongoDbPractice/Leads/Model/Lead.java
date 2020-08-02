package com.example.mongoDbPractice.Leads.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(collection = "LeadData")
public class Lead {

    @Id
    Integer id;
    @NotNull
    String name;
    @NotNull
    String mobileNumber;
    @NotNull
    String concern;
    @NotNull
    String emailId;
    LocalDate creationDate;

    public Lead(Integer id, @NotNull String name, @NotNull String mobileNumber, @NotNull String concern, @NotNull String emailId, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.concern = concern;
        this.emailId = emailId;
        this.creationDate = creationDate;
    }

    public Lead() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
