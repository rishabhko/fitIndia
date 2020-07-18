package com.example.mongoDbPractice.Trainer.Repository;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCourse extends MongoRepository<Course,String> {

     Course findByName(String name);
     Course findByUin(int uin);
}
