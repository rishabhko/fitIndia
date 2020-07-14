package com.example.mongoDbPractice.VideoUpload;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Model.Video;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryVideo;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        video.setVideoPath("/stream/mp4/"+video.getName()+"/"+ video.getCourseId()+"/"+video.getDate());




        Course course=courseOptional.get();
        if (course.getVideos()==null)
        {
            course.setVideos(new ArrayList<Video>());
        }

        String videoIdToBeSet=course.getId()+"_"+(course.getVideos().size()+1);
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
}
