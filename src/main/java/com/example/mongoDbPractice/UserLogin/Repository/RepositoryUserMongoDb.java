package com.example.mongoDbPractice.UserLogin.Repository;

import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RepositoryUserMongoDb extends MongoRepository<User,String> {

    User findByUin(int uin);

}
