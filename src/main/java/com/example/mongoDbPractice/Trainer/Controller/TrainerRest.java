package com.example.mongoDbPractice.Trainer.Controller;

import com.example.mongoDbPractice.Trainer.Model.ReturnObject;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/trainer")
public class TrainerRest {


    @Autowired
    private RepositoryTrainer repositoryTrainer;



    @GetMapping("/getAll")
    private List<TrainerModel> getALlTrainers(@RequestHeader("headerKey") String headerKey)
    {
        if(StringUtils.isEmpty(headerKey) || !headerKey.equals("98f88a00-152a-4627-8157-3814c754c035")) {
            throw new IllegalArgumentException("Header - secret key is empty or wrong");
        }
        return repositoryTrainer.findAll();

    }

    @PostMapping("/saveTrainer")
    private ResponseEntity<ReturnObject> saveTrainer(@RequestBody TrainerModel trainer)
    {

       Optional<TrainerModel> trainerOptional= repositoryTrainer.findById(trainer.getId());
       if (trainerOptional.isPresent())
       {
           return new ResponseEntity<>(new ReturnObject(false,"Trainer already exists",null),HttpStatus.BAD_REQUEST);
       }
       else
       {

           int uniqueUin= generateUin();
           while (repositoryTrainer.findByUin(uniqueUin)!=null)
           {
               uniqueUin=generateUin();

           }
           trainer.setUin(uniqueUin);
           TrainerModel savedTrainer = repositoryTrainer.save(trainer);
           return new ResponseEntity<>(new ReturnObject(true,"Trainer Saved",savedTrainer.getUin()),HttpStatus.BAD_REQUEST);
       }


    }

    private int generateUin() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return number;

    }


    @PostMapping("/getTrainer")
    private ResponseEntity<ReturnObject> getTrainerByemail(@RequestBody TrainerModel trainer)
    {
        Optional<TrainerModel> trainerModelOptional= repositoryTrainer.findById(trainer.getId());

        if (!trainerModelOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnObject(false,"no such trainer exists",null),HttpStatus.BAD_REQUEST);
        }
       else if (trainerModelOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnObject(true,"verified email",trainerModelOptional.get().getUin()),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(new ReturnObject(false,"multiple entries found! error",null),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getByUin/{uin}")
    public Object getTrainerByUin(@PathVariable int uin, @RequestHeader("headerKey") String headerKey)
    {

        if(StringUtils.isEmpty(headerKey) || !headerKey.equals("98f88a00-152a-4627-8157-3814c754c035")) {
            throw new IllegalArgumentException("Header - secret key is empty or wrong");
        }
        TrainerModel trainerModel1=repositoryTrainer.findByUin(uin);
        if (trainerModel1==null)
        {
            return "No such Trainer exists";
        }
        return trainerModel1;
    }

    @PostMapping("/loginTrainer")
    public ResponseEntity<ReturnObject> loginTrainer(@RequestBody TrainerModel trainerModel)
    {
        Optional<TrainerModel> trainerOptional= repositoryTrainer.findById(trainerModel.getId());
        if (!trainerOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnObject(false,"Wrong Id entered",null), HttpStatus.BAD_REQUEST);
        }
        TrainerModel trainer = trainerOptional.get();
        if (!trainerModel.getPassword().equals(trainer.getPassword()))
        {
            return new ResponseEntity<>(new ReturnObject(false,"Wrong password",null), HttpStatus.BAD_REQUEST);
        }
        if (trainerModel.getPassword().equals(trainer.getPassword()))
        {
            return new ResponseEntity<>(new ReturnObject(true,"Verified trainer",trainer.getUin()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ReturnObject(false,"Some error occured",null), HttpStatus.BAD_REQUEST);

    }
}
