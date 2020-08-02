package com.example.mongoDbPractice.Leads.Model;

public class ReturnLeadStatus {
    String status;

    public ReturnLeadStatus(String status) {
        this.status = status;
    }

    public ReturnLeadStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
