package com.example.mongoDbPractice.UserLogin.Model;

public class ReturnLoginUser {
    public Boolean status;
    public String reason;
    public Object uin;

    public ReturnLoginUser(Boolean verified, String reason, Object uin) {
        this.status = verified;
        this.reason = reason;
        this.uin = uin;
    }

    public ReturnLoginUser() {
    }
}
