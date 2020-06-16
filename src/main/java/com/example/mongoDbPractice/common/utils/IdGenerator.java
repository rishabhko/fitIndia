package com.example.mongoDbPractice.common.utils;

import java.security.SecureRandom;

public  class IdGenerator {

    static public String generateId(String firstName,String secondName)
    {
        String id="";
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);
        String a=String.valueOf(firstName.charAt(0))+ String.valueOf(secondName.charAt(0));
        id=a+formatted;
        return id;

    }
}
