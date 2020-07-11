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




    @PostMapping("/joinCourse/{courseId}")
    public ReturnObject joinCourse(@PathVariable String courseId, @RequestBody User user1)
    {
        Optional<User> userOptional= repositoryUserMongoDb.findById(user1.getId());
        if (!userOptional.isPresent())
        {
            return new ReturnObject(false,"user Id doesnt exist",null);
        }
        Optional<Course> courseOptional = repositoryCourse.findById(courseId);
        if (!courseOptional.isPresent())
        {
            return new ReturnObject(false,"course Id doesnt exist",null);
        }
        User user = userOptional.get();
        Course course=courseOptional.get();
        if (user.getCoursesEnrolledIds()==null)
        {
            user.setCoursesEnrolledIds(new ArrayList<String>());
        }
        user.getCoursesEnrolledIds().add(course.getId());
//        if (course.getUsersEnrolled()==null)
//        {
//            course.setUsersEnrolled(new ArrayList<User>());
//        }
//        course.getUsersEnrolled().add(user);




//        Optional<TrainerModel> trainerOptional=repositoryTrainer.findById(course.getTrainerEmailId());
//
//        TrainerModel trainer = trainerOptional.get();
//        int j=0;
//
//
//        for (int i=0; i<trainer.getCourses().size();i++)
//        {
//            Course courseInList=trainer.getCourses().get(i);
//            if (courseInList.getId().equals(course.getId()))
//            {
//                trainer.getCourses().set(i,course);
//
//                j=1;
//                break;
//            }
//
//        }
//        if (j==0)
//        {
//            return new ReturnObject(false,"Error course ID not available in trainer",null);
//
//        }
//        repositoryCourse.findById(course.getId());
//        repositoryTrainer.findById(course.getId());
//        repositoryCourse.save(course);
//        repositoryTrainer.save(trainer);

        Optional<Course_User_Model> course_userOptional= repositoryCourse_user.findById(course.getId());

        if (!course_userOptional.isPresent())
        {
            Course_User_Model course_user_model=new Course_User_Model();
            course_user_model.setId(course.getId());
            course_user_model.setUsersEnrolledIds(new ArrayList<String>());
            course_user_model.getUsersEnrolledIds().add(user.getId());
            repositoryCourse_user.save(course_user_model);
        }
        else {
            Course_User_Model course_user_model= course_userOptional.get();
            course_user_model.getUsersEnrolledIds().add(user.getId());
            repositoryCourse_user.save(course_user_model);

        }
        repositoryUserMongoDb.save(user);




        return  new ReturnObject(true,"enrolled successfully",user);
    }
}
