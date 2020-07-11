package com.example.mongoDbPractice.Trainer.Repository;

import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryTrainer extends MongoRepository<TrainerModel,String> {


//    List<TrainerModel> findByEmailId(String emailId);

    TrainerModel findByUin(int uin);
}
