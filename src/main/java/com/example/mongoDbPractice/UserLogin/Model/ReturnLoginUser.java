package com.example.mongoDbPractice.UserLogin.Model;

public class ReturnLoginUser {
    public Boolean status;
    public String reason;
    public Integer uin;

    public ReturnLoginUser(Boolean status, String reason, Integer uin) {
        this.status = status;
        this.reason = reason;
        this.uin = uin;
    }

    public ReturnLoginUser() {
    }
}
