package com.example.mongoDbPractice.UserLogin.Repository;

import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RepositoryUserMongoDb extends MongoRepository<User,String> {
    public List<User> findByEmailId(String emailId);
}
