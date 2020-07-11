package com.example.mongoDbPractice.Trainer.Repository;

import com.example.mongoDbPractice.Trainer.Model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCourse extends MongoRepository<Course,String> {

     Course findByName(String name);
}
