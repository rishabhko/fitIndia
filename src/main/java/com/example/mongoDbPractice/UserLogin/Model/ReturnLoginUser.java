package com.example.mongoDbPractice.UserLogin.Model;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class ReturnLoginUser {
    public Boolean verified;
    public String reason;
    public Object user;

    public ReturnLoginUser(Boolean verified, String reason, Object user) {
        this.verified = verified;
        this.reason = reason;
        this.user = user;
    }

    public ReturnLoginUser() {
    }
}
