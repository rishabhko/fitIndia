package com.example.mongoDbPractice.VideoUpload;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.Trainer.Model.ReturnObject;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Model.Video;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryVideo;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UploadVideoController {

//    String path="/home/rishabh.kohli/Documents/fitnessAPp/Backend/";

    String path ="/home/arpit/fitback/";

    @Autowired
    private RepositoryVideo repositoryVideo;

    @Autowired
    private RepositoryTrainer repositoryTrainer;

    @Autowired
    private RepositoryCourse repositoryCourse;

//    @RequestMapping(value = "/uploadFile/{courseId}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> uploadFile (@RequestParam("file")MultipartFile file, @PathVariable String courseId, @RequestBody Video video) throws IOException

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile (@RequestParam("file")MultipartFile file, @RequestParam("video1") String video1,@RequestParam MultipartFile thumbnail) throws IOException

    {

        Gson gson = new Gson();
        Video video=gson.fromJson(video1,Video.class);
        video.setName(file.getOriginalFilename());

        int uniqueUin=generateUin();
        while (repositoryVideo.findByUin(uniqueUin)!=null)
        {
            uniqueUin=generateUin();
        }
        video.setUin(uniqueUin);

        Optional<Course> courseOptional=repositoryCourse.findById(video.getCourseId());
        if (!courseOptional.isPresent())
        {
            return new ResponseEntity<>("No such course exists",HttpStatus.BAD_REQUEST);
        }
        File makeDir = new File(path+ video.getCourseId()+"/"+video.getDate()+"/");

        boolean dirCreated = makeDir.mkdir();
        File convertFile= new File(path+ video.getCourseId()+"/"+video.getDate()+"/" +file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();


        File convertFile2= new File(path+ video.getCourseId()+"/"+video.getDate()+"/" +thumbnail.getOriginalFilename());
        convertFile2.createNewFile();
        FileOutputStream fout2 = new FileOutputStream(convertFile2);
        fout2.write(thumbnail.getBytes());
        fout2.close();


        video.setThumbnailPath("http://localhost:8081/video/thumbnail/"+video.getCourseId()+"/"+video.getDate()+"/"+ thumbnail.getOriginalFilename());
        //setPath for thumbnail

        //"/stream/{fileType}/{fileName}/{trainerName}/{date}")
        video.setVideoPath("http://localhost:8082/stream/mp4/"+video.getName()+"/"+ video.getCourseId()+"/"+video.getDate());

        Course course=courseOptional.get();
        if (course.getVideos()==null)
        {
            course.setVideos(new ArrayList<Video>());
        }
        int k=course.getVideos().size()+1;

        String videoIdToBeSet=course.getId()+"_"+k;
        while(repositoryVideo.findById(videoIdToBeSet).isPresent())
        {
            k++;
            videoIdToBeSet=course.getId()+"_"+k;
        }
        video.setId(videoIdToBeSet);
        course.getVideos().add(video);

        repositoryCourse.save(course);
        repositoryVideo.save(video);
        Optional<TrainerModel> trainerOptional= repositoryTrainer.findById(course.getTrainerEmailId());
        TrainerModel trainer= trainerOptional.get();
        int j=0;
        for (int i=0; i<trainer.getCourses().size();i++)
        {
            Course courseInList=trainer.getCourses().get(i);
            if (courseInList.getId().equals(course.getId()))
            {
                trainer.getCourses().set(i,course);
                repositoryTrainer.save(trainer);
                j=1;
                break;
            }
        }
        if (j==0)
        {
            return new ResponseEntity<>("Course id not matching in trainer",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

    @GetMapping("/getVideoById/{videoId}")
    public ResponseEntity<Object> getVideoById(@PathVariable String videoId)
    {
        if (videoId==null || StringUtils.isEmpty(videoId))
        {
            return new ResponseEntity<>("Video cant be fetched wrong id",HttpStatus.BAD_REQUEST);
        }
        Optional<Video> videoOptional = repositoryVideo.findById(videoId);
        if (!videoOptional.isPresent())
        {
            return new ResponseEntity<>("No such id exists",HttpStatus.BAD_REQUEST);
        }
        Video video=videoOptional.get();

        return new ResponseEntity<>(video,HttpStatus.OK);
    }


    @GetMapping("/getVideoByUin/{uin}")
    public ResponseEntity<Object> getVideoByUin(@PathVariable int uin)
    {
        if ( StringUtils.isEmpty(uin))
        {
            return new ResponseEntity<>("Video cant be fetched wrong id",HttpStatus.BAD_REQUEST);
        }
        Video video = repositoryVideo.findByUin(uin);
        if (video==null)
        {
            return new ResponseEntity<>("No such id exists",HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(video,HttpStatus.OK);
    }


    @PostMapping("/deleteVideoByUin/{uin}")
    public ResponseEntity<String> deleteVideo(@PathVariable int uin)
    {
        Video video = repositoryVideo.findByUin(uin);
        if (video==null)
        {
            return new ResponseEntity<>("Wrong uin entered",HttpStatus.BAD_REQUEST);
        }
       Optional<Course> courseOptional = repositoryCourse.findById(video.getCourseId());
        if (!courseOptional.isPresent())
        {
            return new ResponseEntity<>("No such course",HttpStatus.BAD_REQUEST);

        }
        Course course=courseOptional.get();
        Optional<TrainerModel> trainerModelOptional=repositoryTrainer.findById(course.getTrainerEmailId());
        if (!trainerModelOptional.isPresent())
        {
            return new ResponseEntity<>("No such trainer",HttpStatus.BAD_REQUEST);
        }

        try {
            FileUtils.deleteDirectory(new File(path+video.getCourseId()+"/"+video.getDate()));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        TrainerModel trainer=trainerModelOptional.get();
        repositoryVideo.delete(video);
        for (int i=0;i<course.getVideos().size();i++)
        {
            Video videoFromCourse=course.getVideos().get(i);
            if (videoFromCourse.getId().equals(video.getId()))
            {
                course.getVideos().remove(i);
                break;
            }

        }
        repositoryCourse.save(course);
        for (int i=0;i<trainer.getCourses().size();i++)
        {
            Course courseFromTrainer=trainer.getCourses().get(i);
            if (courseFromTrainer.getId().equals(course.getId()))
            {
                trainer.getCourses().set(i,course);
                repositoryTrainer.save(trainer);
                break;
            }
        }
        return new ResponseEntity<>("Video deleted",HttpStatus.OK);
    }

    @PostMapping("/updateVideoByUin")
    public ResponseEntity<ReturnObject> deleteVideo(@RequestBody Video videoNew)
    {
        if (videoNew.getUin()==null)
        {
            return new ResponseEntity<>(new ReturnObject(false,"Invalid uin",null),HttpStatus.BAD_REQUEST);
        }


        Video videoSaved = repositoryVideo.findByUin(videoNew.getUin());
        if (videoSaved==null)
        {
            return new ResponseEntity<>(new ReturnObject(false,"Wrong uin entered",null),HttpStatus.BAD_REQUEST);
        }
        Optional<Course> courseOptional = repositoryCourse.findById(videoSaved.getCourseId());
        if (!courseOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnObject(false,"No such course",null),HttpStatus.BAD_REQUEST);

        }
        Course course=courseOptional.get();
        Optional<TrainerModel> trainerModelOptional=repositoryTrainer.findById(course.getTrainerEmailId());
        if (!trainerModelOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnObject(false,"No such trainer exists",null),HttpStatus.BAD_REQUEST);
        }
//        if (videoNew.getName()!=null)
//        {
//            videoSaved.setName(videoNew.getName());
//        }
        if (videoNew.getDescription()!=null)
        {
            videoSaved.setDescription(videoNew.getDescription());
        }
        if (videoNew.getTitle()!=null)
        {
            videoSaved.setTitle(videoNew.getTitle());
        }



        TrainerModel trainer=trainerModelOptional.get();
        repositoryVideo.save(videoSaved);
        for (int i=0;i<course.getVideos().size();i++)
        {
            Video videoFromCourse=course.getVideos().get(i);
            if (videoFromCourse.getId().equals(videoSaved.getId()))
            {
                course.getVideos().set(i,videoSaved);
                break;
            }

        }
        repositoryCourse.save(course);
        for (int i=0;i<trainer.getCourses().size();i++)
        {
            Course courseFromTrainer=trainer.getCourses().get(i);
            if (courseFromTrainer.getId().equals(course.getId()))
            {
                trainer.getCourses().set(i,course);
                repositoryTrainer.save(trainer);
                break;
            }
        }
        return new ResponseEntity<>(new ReturnObject(true,"Video updated successfully",videoSaved.getUin()),HttpStatus.OK);
    }







    private int generateUin() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return number;
    }
}
