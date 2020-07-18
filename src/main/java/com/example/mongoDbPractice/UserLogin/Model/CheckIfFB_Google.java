package com.example.mongoDbPractice.UserLogin.Model;

//import com.sun.org.apache.xpath.internal.operations.Bool;

public class CheckIfFB_Google {

    String id;
    String oath;


    public CheckIfFB_Google(String id, String oath) {
        this.id = id;
        this.oath = oath;
    }

    public CheckIfFB_Google() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOath() {
        return oath;
    }

    public void setOath(String oath) {
        this.oath = oath;
    }
}
