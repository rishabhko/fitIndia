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
    String path = "/home/arpit/fitback/";

    @Autowired
    private RepositoryTrainer repositoryTrainer;

    @Autowired
    private RepositoryCourse repositoryCourse;


    @GetMapping("/getAll")
    public List<Course> getAllCourses() {
        return repositoryCourse.findAll();
    }


    @PostMapping("/getCourse")
    public ReturnObject getCourseById(@RequestBody Course courseFromRequest) {
        Optional<Course> coursesOptional = repositoryCourse.findById(courseFromRequest.getId());
        Boolean present = coursesOptional.isPresent();


        if (present) {
            Course course = coursesOptional.get();
            return new ReturnObject(true, "course found", course);

        }
        return new ReturnObject(false, "No course by such Id", null);

    }

    @PostMapping("/saveCourse")
    public ResponseEntity<ReturnObject> saveCourseById(@RequestBody Course course) {
        if (course.getTrainerEmailId() == null) {
            return new ResponseEntity<>(new ReturnObject(false, "trainer id not valid", null), HttpStatus.BAD_REQUEST);
        }

        Optional<TrainerModel> trainerOptional = repositoryTrainer.findById(course.getTrainerEmailId());
        if (!trainerOptional.isPresent()) {
            return new ResponseEntity<>(new ReturnObject(false, "trainer not found or illegal data", null), HttpStatus.BAD_REQUEST);
        }

//        Course courseByName = repositoryCourse.findByName(course.getName());
//        if (courseByName!=null)
//        {
//            return new ReturnObject(false,"course with same name already exists", null);
//
//        }


        int uniqueUin = generateUin();
        while (repositoryCourse.findByUin(uniqueUin) != null) {
            uniqueUin = generateUin();
        }
        course.setUin(uniqueUin);
        course.setCategory(course.getCategory().toUpperCase());


        TrainerModel trainer = trainerOptional.get();
        String idTOBeSet = generateRandomId(trainer.getName());
        while (repositoryCourse.findById(idTOBeSet).isPresent()) {
            idTOBeSet = generateRandomId(trainer.getName());
        }
        course.setId(idTOBeSet);

        File makeDir = new File(path + course.getId() + "/");

        boolean dirCreated = makeDir.mkdir();


        if (trainer.getCourses() == null) {
            trainer.setCourses(new ArrayList<Course>());
        }
        trainer.getCourses().add(course);



        repositoryTrainer.save(trainer);
        repositoryCourse.save(course);
        return new ResponseEntity<>(new ReturnObject(true, "saved successfully", course.getUin()), HttpStatus.OK);
    }

    @PostMapping("/saveCourseByUin/{trainerUin}")
    public ResponseEntity<ReturnObject> saveCourseByUin(@RequestBody Course course, @PathVariable Integer trainerUin) {


        TrainerModel trainer = repositoryTrainer.findByUin(trainerUin);
        if (trainer == null) {
            return new ResponseEntity<>(new ReturnObject(false, "trainer not found or illegal data", null), HttpStatus.BAD_REQUEST);
        }

//        Course courseByName = repositoryCourse.findByName(course.getName());
//        if (courseByName!=null)
//        {
//            return new ReturnObject(false,"course with same name already exists", null);
//
//        }


        int uniqueUin = generateUin();
        while (repositoryCourse.findByUin(uniqueUin) != null) {
            uniqueUin = generateUin();
        }
        course.setUin(uniqueUin);
        course.setCategory(course.getCategory().toUpperCase());


        String idTOBeSet = generateRandomId(trainer.getName());
        while (repositoryCourse.findById(idTOBeSet).isPresent()) {
            idTOBeSet = generateRandomId(trainer.getName());
        }
        course.setId(idTOBeSet);
        course.setTrainerEmailId(trainer.getId());

        File makeDir = new File(path + course.getId() + "/");

        boolean dirCreated = makeDir.mkdir();


        if (trainer.getCourses() == null) {
            trainer.setCourses(new ArrayList<Course>());
        }
        trainer.getCourses().add(course);

        repositoryTrainer.save(trainer);
        repositoryCourse.save(course);
        return new ResponseEntity<>(new ReturnObject(true, "saved successfully", course.getUin()), HttpStatus.OK);
    }

    @GetMapping("/getCourseByUin/{uin}")
    public ResponseEntity<Object> getVideoByUin(@PathVariable int uin) {
        if (StringUtils.isEmpty(uin)) {
            return new ResponseEntity<>("Course cant be fetched wrong id", HttpStatus.BAD_REQUEST);
        }
        Course course = repositoryCourse.findByUin(uin);
        if (course == null) {
            return new ResponseEntity<>("No such id exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/getCourseById/{courseId}")
    public ResponseEntity<Object> getVideoByUin(@PathVariable String courseId) {
        if (StringUtils.isEmpty(courseId)) {
            return new ResponseEntity<>("Video cant be fetched wrong id", HttpStatus.BAD_REQUEST);
        }
        Optional<Course> courseOptional = repositoryCourse.findById(courseId);
        if (!courseOptional.isPresent()) {
            return new ResponseEntity<>("No such id exists", HttpStatus.BAD_REQUEST);
        }
        Course course = courseOptional.get();
        return new ResponseEntity<>(course, HttpStatus.OK);
    }


    @PostMapping("/updateCourseByUin")
    public ResponseEntity<ReturnObject> updateCourseByUin(@RequestBody Course courseNew) {
        if (courseNew.getUin() == null) {
            return new ResponseEntity<>(new ReturnObject(false, "invalid uin", null), HttpStatus.BAD_REQUEST);
        }
        Course courseSaved = repositoryCourse.findByUin(courseNew.getUin());
        if (courseSaved == null) {
            return new ResponseEntity<>(new ReturnObject(false, "Np such course exists", null), HttpStatus.BAD_REQUEST);
        }
        if (courseNew.getName() != null) {
            courseSaved.setName(courseNew.getName());
        }
        if (courseNew.getDescription() != null) {
            courseSaved.setDescription(courseNew.getDescription());
        }
        if (courseNew.getFees() != null) {
            courseSaved.setFees(courseNew.getFees());
        }
        if (courseNew.getCategory() != null) {
            courseSaved.setCategory(courseNew.getCategory().toUpperCase());
        }


        Optional<TrainerModel> trainerModelOptional = repositoryTrainer.findById(courseSaved.getTrainerEmailId());
        if (!trainerModelOptional.isPresent()) {
            return new ResponseEntity<>(new ReturnObject(false, "Database error trainer not found", null), HttpStatus.BAD_REQUEST);
        }
        TrainerModel trainer = trainerModelOptional.get();

        for (int i = 0; i < trainer.getCourses().size(); i++) {
            Course courseInTrainer = trainer.getCourses().get(i);
            if (courseInTrainer.getId().equals(courseSaved.getId())) {
                trainer.getCourses().set(i, courseSaved);
                break;
            }
        }
        repositoryTrainer.save(trainer);
        repositoryCourse.save(courseSaved);
        return new ResponseEntity<>(new ReturnObject(true, "Updated successfully", courseSaved.getUin()), HttpStatus.OK);

    }

    @GetMapping("/searchCourses/{searchString}")
    public ResponseEntity<List<Course>> searchCourseByString(@PathVariable String searchString)
    {
        List<Course> courseList = repositoryCourse.findAll();
        List<Course> returnCourseList=new ArrayList<>();
        for (Course courseSaved:courseList) {
//            if (org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString)>0)
//           int x= org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString);

            if (courseSaved.getName().toLowerCase().contains(searchString.toLowerCase())||courseSaved.getDescription().toLowerCase().contains(searchString.toLowerCase()))
            {
                returnCourseList.add(courseSaved);
            }
        }
        return new ResponseEntity<>(returnCourseList,HttpStatus.OK);

    }

    @GetMapping("/searchCoursesByCategory/{category}")
    public ResponseEntity<List<Course>> searchCourseByCategoty(@PathVariable String category)
    {
        List<Course> courseList = repositoryCourse.findAll();
        List<Course> returnCourseList=new ArrayList<>();
        for (Course courseSaved:courseList) {
//            if (org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString)>0)
//           int x= org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString);

            if (courseSaved.getCategory().toLowerCase().contains(category.toLowerCase()))
            {
                returnCourseList.add(courseSaved);
            }
        }
        return new ResponseEntity<>(returnCourseList,HttpStatus.OK);

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
