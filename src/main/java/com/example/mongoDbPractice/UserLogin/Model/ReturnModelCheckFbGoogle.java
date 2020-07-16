package com.example.mongoDbPractice.UserLogin.Model;

public class ReturnModelCheckFbGoogle {
    public String status;
    public String reason;
    public Object uin;

    public ReturnModelCheckFbGoogle(String status, String reason, Object uin) {
        this.status = status;
        this.reason = reason;
        this.uin = uin;
    }

    public ReturnModelCheckFbGoogle() {
    }
}
