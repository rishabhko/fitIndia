package com.example.mongoDbPractice.UserLogin.Repository;

import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryUserMongoDb extends MongoRepository<User,Long> {
    public User findByName(String name);
}
