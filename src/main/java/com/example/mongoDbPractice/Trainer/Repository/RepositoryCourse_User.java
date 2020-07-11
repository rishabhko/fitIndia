package com.example.mongoDbPractice.Trainer.Repository;

import com.example.mongoDbPractice.Trainer.Model.Course_User_Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCourse_User extends MongoRepository<Course_User_Model,String> {

}
