package com.example.mongoDbPractice.Trainer.Controller;

import com.example.mongoDbPractice.Trainer.Model.*;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import com.example.mongoDbPractice.UserLogin.Model.User;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/course")
public class CourseRest {

//        String path="/home/rishabh.kohli/Documents/fitnessAPp/Backend/";
//    String path = "/home/arpit/fitback/";

    String path="/home/deploy/database/";

    @Autowired
    private RepositoryTrainer repositoryTrainer;

    @Autowired
    private RepositoryCourse repositoryCourse;

    @Autowired
    private RepositoryUserMongoDb repositoryUserMongoDb;


    @GetMapping("/getAll")
    public ResponseEntity<List<ReturnTrainer_Course_Video>> getAllCourses() {


        List<Course> returnlIst=new ArrayList<>();
        List<ReturnTrainer_Course_Video> returnTrainer_course_videosList= new ArrayList<>();
         List<Course> allCourseSaved= repositoryCourse.findAll();
        for (Course course:allCourseSaved) {
            if (course.getValid()==null || course.getValid())
            {
               Optional<TrainerModel> trainerModelOptional=  repositoryTrainer.findById(course.getTrainerEmailId());
               TrainerModel trainer =trainerModelOptional.get();
//               List<Integer> videoUinList = new ArrayList<>();
//                for (Video video:course.getVideos()) {
//                    videoUinList.add(video.getUin());
//                }

                returnTrainer_course_videosList.add(new ReturnTrainer_Course_Video(trainer.getName(),trainer.getCertificatePaths(),trainer.getUin(),trainer.getProfilePicPath(),course));
            }
        }
        return new ResponseEntity<>(returnTrainer_course_videosList,HttpStatus.OK);
    }


    @PostMapping("/getCourse")
    public ReturnObject getCourseById(@RequestBody Course courseFromRequest) {
        Optional<Course> coursesOptional = repositoryCourse.findById(courseFromRequest.getId());
        Boolean present = coursesOptional.isPresent();


        if (present) {
            Course course = coursesOptional.get();
            if (course.getValid()==null || course.getValid()){
                return new ReturnObject(true, "course found", course);
            }
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
        course.setValid(true);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
        course.setCreationDate(LocalDate.now());
        course.setUpdationData(LocalDate.now());


        TrainerModel trainer = trainerOptional.get();
        String idTOBeSet = generateRandomId(trainer.getName().replaceAll("\\s", ""));
        while (repositoryCourse.findById(idTOBeSet).isPresent()) {
            idTOBeSet = generateRandomId(trainer.getName().replaceAll("\\s", ""));
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
        course.setValid(true);
        course.setCreationDate(LocalDate.now());
        course.setUpdationData(LocalDate.now());


        String idTOBeSet = generateRandomId(trainer.getName().replaceAll("\\s", ""));
        while (repositoryCourse.findById(idTOBeSet).isPresent()) {
            idTOBeSet = generateRandomId(trainer.getName().replaceAll("\\s", ""));
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
            return new ResponseEntity<>("Course cant be fetched wrong uin", HttpStatus.BAD_REQUEST);
        }
        Course course = repositoryCourse.findByUin(uin);
        if (course == null) {
            return new ResponseEntity<>("No such uin exists", HttpStatus.BAD_REQUEST);
        }
        if (course.getValid()==null ||course.getValid())
        {
            Optional<TrainerModel> trainerModelOptional=  repositoryTrainer.findById(course.getTrainerEmailId());
            TrainerModel trainer =trainerModelOptional.get();


            return new ResponseEntity<>(new ReturnTrainer_Course_Video(trainer.getName(),trainer.getCertificatePaths(),trainer.getUin(),trainer.getProfilePicPath(),course),HttpStatus.OK);
//            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>("No such course exists", HttpStatus.BAD_REQUEST);
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
        if (course.getValid()==null ||course.getValid())
        {
            return new ResponseEntity<>(course, HttpStatus.OK);
        }
        return new ResponseEntity<>("No such id exists", HttpStatus.BAD_REQUEST);
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
//        course.setCreationDate(LocalDate.now());
        courseSaved.setUpdationData(LocalDate.now());


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


    @PostMapping("/deleteCourseByUin/{uin}")
    public ResponseEntity<ReturnObject> deleteCourseByUin(@PathVariable Integer uin) {
        if (uin == null) {
            return new ResponseEntity<>(new ReturnObject(false, "invalid uin", null), HttpStatus.BAD_REQUEST);
        }
        Course courseSaved = repositoryCourse.findByUin(uin);



        Optional<TrainerModel> trainerModelOptional = repositoryTrainer.findById(courseSaved.getTrainerEmailId());
        if (!trainerModelOptional.isPresent()) {
            return new ResponseEntity<>(new ReturnObject(false, "Database error trainer not found", null), HttpStatus.BAD_REQUEST);
        }
        TrainerModel trainer = trainerModelOptional.get();
        courseSaved.setValid(false);

        for (int i = 0; i < trainer.getCourses().size(); i++) {
            Course courseInTrainer = trainer.getCourses().get(i);
            if (courseInTrainer.getId().equals(courseSaved.getId())) {
                trainer.getCourses().remove(i);
//                trainer.getCourses().set(i, courseSaved);
                break;
            }
        }
        repositoryTrainer.save(trainer);
        repositoryCourse.save(courseSaved);
        return new ResponseEntity<>(new ReturnObject(true, "Deleted successfully", courseSaved.getUin()), HttpStatus.OK);

    }

    @GetMapping("/searchCourses/{searchString}/{searchByN}")
    public ResponseEntity<List<ReturnTrainer_Course_Video>> searchCourseByString(@PathVariable String searchString,@PathVariable Integer searchByN)
    {
        List<Course> courseList = repositoryCourse.findAll();
        int i=0;
        List<Course> returnCourseList=new ArrayList<>();
        for (Course courseSaved:courseList) {
//            if (org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString)>0)
//           int x= org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString);


            if (courseSaved.getName().toLowerCase().contains(searchString.toLowerCase())||courseSaved.getDescription().toLowerCase().contains(searchString.toLowerCase()))
            {
                if (courseSaved.getValid()==null || courseSaved.getValid())
                {
                    returnCourseList.add(courseSaved);

                }
            }

        }
        Collections.sort(returnCourseList);
        List<Course> returnCourseListFinal=new ArrayList<>();
        for (Course course:returnCourseList) {
            if (i<searchByN)
            {
                returnCourseListFinal.add(course);
                i++;
            }
            else break;
        }



        List<ReturnTrainer_Course_Video> returnTrainer_course_videosList= new ArrayList<>();

        for (Course course:returnCourseListFinal) {

            Optional<TrainerModel> trainerModelOptional=  repositoryTrainer.findById(course.getTrainerEmailId());
                TrainerModel trainer =trainerModelOptional.get();
//                List<Integer> videoUinList = new ArrayList<>();
//
//                if (course.getVideos()!=null) {
//                    for (Video video : course.getVideos()) {
//                        videoUinList.add(video.getUin());
//                    }
//                }

                returnTrainer_course_videosList.add(new ReturnTrainer_Course_Video(trainer.getName(),trainer.getCertificatePaths(),trainer.getUin(),trainer.getProfilePicPath(),course));

        }
        return new ResponseEntity<>(returnTrainer_course_videosList,HttpStatus.OK);

//        return new ResponseEntity<>(returnCourseListFinal,HttpStatus.OK);

    }

    @GetMapping("/searchCoursesByCategory/{category}/{searchByN}")
    public ResponseEntity<List<ReturnTrainer_Course_Video>> searchCourseByCategoty(@PathVariable String category,@PathVariable Integer searchByN)
    {
        List<Course> courseList = repositoryCourse.findAll();
        List<Course> returnCourseList=new ArrayList<>();
        int i=0;
        for (Course courseSaved:courseList) {
//            if (org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString)>0)
//            int x= org.apache.commons.lang3.StringUtils.compareIgnoreCase(courseSaved.getName(), searchString);



            if (courseSaved.getCategory().toLowerCase().contains(category.toLowerCase()))
            {
                if (courseSaved.getValid()==null || courseSaved.getValid())
                returnCourseList.add(courseSaved);
            }
        }
        Collections.sort(returnCourseList);


        List<Course> returnCourseListFinal=new ArrayList<>();
        for (Course course:returnCourseList) {
            if (i<searchByN)
            {
                returnCourseListFinal.add(course);
                i++;
            }
            else break;
        }


        List<ReturnTrainer_Course_Video> returnTrainer_course_videosList= new ArrayList<>();

        for (Course course:returnCourseListFinal) {

            Optional<TrainerModel> trainerModelOptional=  repositoryTrainer.findById(course.getTrainerEmailId());
            TrainerModel trainer =trainerModelOptional.get();
//            List<Integer> videoUinList = new ArrayList<>();
//            for (Video video:course.getVideos()) {
//                videoUinList.add(video.getUin());
//            }

            returnTrainer_course_videosList.add(new ReturnTrainer_Course_Video(trainer.getName(),trainer.getCertificatePaths(),trainer.getUin(),trainer.getProfilePicPath(),course));

        }
        return new ResponseEntity<>(returnTrainer_course_videosList,HttpStatus.OK);
//        return new ResponseEntity<>(returnCourseList,HttpStatus.OK);

    }


    @GetMapping("/getCoursesByUserUin/{userUin}")
    public ResponseEntity<Object> getenrolledCoursesList(@PathVariable Integer userUin)
    {
        User user=repositoryUserMongoDb.findByUin(userUin);
        if (user==null)
        {
            return new ResponseEntity<>("No such user registered",HttpStatus.BAD_REQUEST);
        }

        List<User_Courses_ReturnModel> user_courses_returnModelList= new ArrayList<>();
        for (String courseId:user.getCoursesEnrolledIds()) {
           Optional<Course> courseOptional= repositoryCourse.findById(courseId);
           if (!courseOptional.isPresent())
           {
               return new ResponseEntity<>("Inconsistend data! Fatal error. No such course",HttpStatus.BAD_REQUEST);
           }
          Course course= courseOptional.get();
          Optional<TrainerModel> trainerModelOptional =repositoryTrainer.findById(course.getTrainerEmailId());
            if (!trainerModelOptional.isPresent())
            {
                return new ResponseEntity<>("Inconsistend data! Fatal error. No such trainer",HttpStatus.BAD_REQUEST);
            }
            TrainerModel trainer = trainerModelOptional.get();
            user_courses_returnModelList.add(new User_Courses_ReturnModel(course.getUin(),trainer.getUin(),course.getThumbnailPath(),trainer.getName(),course.getDescription(),course.getName()));
        }
        return new ResponseEntity<>(user_courses_returnModelList,HttpStatus.OK);
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
