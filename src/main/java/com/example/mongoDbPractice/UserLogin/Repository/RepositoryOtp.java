package com.example.mongoDbPractice.UserLogin.Repository;

import com.example.mongoDbPractice.UserLogin.Model.EmailFormat;
import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositoryOtp extends MongoRepository<EmailFormat,String> {

    public EmailFormat findByEmail(String emailId);
}
