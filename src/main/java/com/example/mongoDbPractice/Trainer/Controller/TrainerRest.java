package com.example.mongoDbPractice.Trainer.Controller;

import com.example.mongoDbPractice.Trainer.Model.ReturnObject;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainer")
public class TrainerRest {


    @Autowired
    private RepositoryTrainer repositoryTrainer;



    @GetMapping("/getAll")
    private List<TrainerModel> getALlTrainers()
    {
        return repositoryTrainer.findAll();

    }

    @PostMapping("/saveTrainer")
    private ReturnObject saveTrainer(@RequestBody TrainerModel trainer)
    {

       Optional<TrainerModel> trainerOptional= repositoryTrainer.findById(trainer.getId());
       if (trainerOptional.isPresent())
       {
           return new ReturnObject(false,"Trainer already exists",null);
       }
       else
       {
           TrainerModel savedTrainer = repositoryTrainer.save(trainer);
           return new ReturnObject(true,"Trainer Saved",savedTrainer);
       }


    }


    @GetMapping("/getTrainer")
    private ReturnObject getTrainerByemail(@RequestBody TrainerModel trainer)
    {
        Optional<TrainerModel> trainerModelOptional= repositoryTrainer.findById(trainer.getId());

        if (!trainerModelOptional.isPresent())
        {
            return new ReturnObject(false,"no such trainer exists",null);
        }
       else if (trainerModelOptional.isPresent())
        {
            return new ReturnObject(true,"verified email",trainerModelOptional.get());
        }
        else
            return new ReturnObject(false,"multiple entries found!! Data error",null);
    }
}
