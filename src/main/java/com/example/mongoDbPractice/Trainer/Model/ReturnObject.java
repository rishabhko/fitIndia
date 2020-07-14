package com.example.mongoDbPractice.Trainer.Model;

public class ReturnObject {
    Boolean status;
    String reason;
    Object uin;

    public ReturnObject(Boolean status, String reason, Object uin) {
        this.status = status;
        this.reason = reason;
        this.uin = uin;
    }

    public ReturnObject() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getUin() {
        return uin;
    }

    public void setUin(Object uin) {
        this.uin = uin;
    }
}
