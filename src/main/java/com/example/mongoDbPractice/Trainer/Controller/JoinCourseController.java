package com.example.mongoDbPractice.Trainer.Controller;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.Trainer.Model.Course_User_Model;
import com.example.mongoDbPractice.Trainer.Model.ReturnObject;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse_User;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import com.example.mongoDbPractice.UserLogin.Model.User;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class JoinCourseController {


    @Autowired
    private RepositoryCourse repositoryCourse;

    @Autowired
    private RepositoryUserMongoDb repositoryUserMongoDb;

    @Autowired
    private RepositoryTrainer repositoryTrainer;

    @Autowired
    private RepositoryCourse_User repositoryCourse_user;




    @PostMapping("/joinCourse/{courseUin}")
    public ResponseEntity<ReturnObject> joinCourse(@PathVariable Integer courseUin, @RequestBody User user1)
    {
        if (user1.getUin()==null)
        {
            return new ResponseEntity<>(new ReturnObject(false,"User uin can't be null",null), HttpStatus.BAD_REQUEST);
        }

        User user= repositoryUserMongoDb.findByUin(user1.getUin());
        if (user==null)
        {
            return new ResponseEntity<>(new ReturnObject(false,"user Id doesnt exist",null),HttpStatus.BAD_REQUEST);
        }
        Course course = repositoryCourse.findByUin(courseUin);
        if (course==null)
        {
            return new ResponseEntity<>(new ReturnObject(false,"course Id doesnt exist",null),HttpStatus.BAD_REQUEST);
        }
        if (user.getCoursesEnrolledIds()==null)
        {
            user.setCoursesEnrolledIds(new ArrayList<String>());
        }
        if (user.getCoursesEnrolledIds().contains(course.getId()))
        {
            return new ResponseEntity<>(new ReturnObject(false,"Already joined this course",null),HttpStatus.BAD_REQUEST);
        }
        user.getCoursesEnrolledIds().add(course.getId());
        Optional<Course_User_Model> course_userOptional= repositoryCourse_user.findById(course.getId());
        if (!course_userOptional.isPresent())
        {
            Course_User_Model course_user_model=new Course_User_Model();
            course_user_model.setId(course.getId());
            course_user_model.setUsersEnrolledIds(new ArrayList<String>());
            course_user_model.setUsersEnrolledUins(new ArrayList<Integer>());
            course_user_model.getUsersEnrolledIds().add(user.getId());
            course_user_model.getUsersEnrolledUins().add(user.getUin());
            repositoryCourse_user.save(course_user_model);
        }
        else {
            Course_User_Model course_user_model= course_userOptional.get();
            course_user_model.getUsersEnrolledIds().add(user.getId());
            course_user_model.getUsersEnrolledUins().add(user.getUin());
            repositoryCourse_user.save(course_user_model);

        }
        repositoryUserMongoDb.save(user);
        return  new ResponseEntity<>(new ReturnObject(true,"enrolled successfully",user),HttpStatus.OK);
    }

    @GetMapping("/checkIfEnrolled/{courseUin}/{userUin}")
    public ResponseEntity<Boolean> ifEnrolled(@PathVariable Integer courseUin,@PathVariable Integer userUin)
    {
       Course course= repositoryCourse.findByUin(courseUin);
       if (course==null)
       {
           return new ResponseEntity<>(false,HttpStatus.OK);
       }
       Optional<Course_User_Model> course_user_modelOptional = repositoryCourse_user.findById(course.getId());
       if (!course_user_modelOptional.isPresent())
       {
           return new ResponseEntity<>(false,HttpStatus.OK);
       }
       Course_User_Model course_user_model=course_user_modelOptional.get();
       if (course_user_model.getUsersEnrolledUins().contains(userUin))
       {
           return new ResponseEntity<>(true,HttpStatus.OK);
       }
       else return new ResponseEntity<>(false,HttpStatus.OK);
    }
}
