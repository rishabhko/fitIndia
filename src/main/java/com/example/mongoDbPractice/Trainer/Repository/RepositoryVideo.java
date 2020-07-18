package com.example.mongoDbPractice.Trainer.Repository;

import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryVideo extends MongoRepository<Video,String> {

//    Video findByDate(String date);
    Video findByUin(int uin);
}
