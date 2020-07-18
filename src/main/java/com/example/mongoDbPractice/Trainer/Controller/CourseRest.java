package com.example.mongoDbPractice.Trainer.Controller;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.Trainer.Model.ReturnObject;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/course")
public class CourseRest {

//    String path="/home/rishabh.kohli/Documents/fitnessAPp/Backend/";
    String path ="/home/arpit/fitback/";

    @Autowired
    private RepositoryTrainer repositoryTrainer;

    @Autowired
    private RepositoryCourse repositoryCourse;


    @PostMapping("getAll")
    public List<Course> getAllCourses()
    {
        return repositoryCourse.findAll();
    }


    @PostMapping("/getCourse")
    public ReturnObject getCourseById(@RequestBody Course courseFromRequest)
    {
        Optional<Course> coursesOptional =repositoryCourse.findById(courseFromRequest.getId());
       Boolean present= coursesOptional.isPresent();


       if (present)
       {
           Course course= coursesOptional.get();
           return new ReturnObject(true,"course found",course);

       }
       return new ReturnObject(false,"No course by such Id",null);

    }

    @PostMapping("/saveCourse")
    public ReturnObject saveCourse(@RequestBody Course course)
    {

        Optional<TrainerModel> trainerOptional=repositoryTrainer.findById(course.getTrainerEmailId());
        if (!trainerOptional.isPresent())
        {
            return new ReturnObject(false,"trainer not found or illegal data", null);
        }

        Course courseByName = repositoryCourse.findByName(course.getName());
        if (courseByName!=null)
        {
            return new ReturnObject(false,"course with same name already exists", null);

        }


        int uniqueUin=generateUin();
        while (repositoryCourse.findByUin(uniqueUin)!=null)
        {
            uniqueUin=generateUin();
        }
        course.setUin(uniqueUin);




        TrainerModel trainer=trainerOptional.get();
        String idTOBeSet=generateRandomId(trainer.getName());
        while(repositoryCourse.findById(idTOBeSet).isPresent())
        {
            idTOBeSet=generateRandomId(trainer.getName());
        }
        course.setId(idTOBeSet);

        File makeDir = new File(path+ course.getId()+"/");

        boolean dirCreated = makeDir.mkdir();


        if (trainer.getCourses()==null)
        {
            trainer.setCourses(new ArrayList<Course>());
        }
        trainer.getCourses().add(course);

        repositoryTrainer.save(trainer);
        repositoryCourse.save(course);
        return new ReturnObject(true,"saved successfully",course);
    }

    @GetMapping("/getCourseByUin/{uin}")
    public ResponseEntity<Object> getVideoByUin(@PathVariable int uin)
    {
        if ( StringUtils.isEmpty(uin))
        {
            return new ResponseEntity<>("Course cant be fetched wrong id", HttpStatus.BAD_REQUEST);
        }
        Course course = repositoryCourse.findByUin(uin);
        if (course==null)
        {
            return new ResponseEntity<>("No such id exists",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(course,HttpStatus.OK);
    }

    @GetMapping("/getCourseById/{courseId}")
    public ResponseEntity<Object> getVideoByUin(@PathVariable String courseId)
    {
        if ( StringUtils.isEmpty(courseId))
        {
            return new ResponseEntity<>("Video cant be fetched wrong id",HttpStatus.BAD_REQUEST);
        }
        Optional<Course> courseOptional = repositoryCourse.findById(courseId);
        if (!courseOptional.isPresent())
        {
            return new ResponseEntity<>("No such id exists",HttpStatus.BAD_REQUEST);
        }
        Course course=courseOptional.get();
        return new ResponseEntity<>(course,HttpStatus.OK);
    }




    String generateRandomId(String name)
    {
        Random random = new Random();
        String randomNumber = String.format("%04d", random.nextInt(10000));
        String setId=name.trim().trim()+randomNumber;
        return setId;

    }

    private int generateUin() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return number;

    }


}
