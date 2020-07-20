package com.example.mongoDbPractice.Thumbnail;

import com.example.mongoDbPractice.Trainer.Model.Course;
import com.example.mongoDbPractice.Trainer.Model.TrainerModel;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryCourse;
import com.example.mongoDbPractice.Trainer.Repository.RepositoryTrainer;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ThumbnailController {


        String path="/home/rishabh.kohli/Documents/fitnessAPp/Backend/";
//            String path ="/home/arpit/fitback/";

    @Autowired
    private RepositoryCourse repositoryCourse;

    @Autowired
    private RepositoryTrainer repositoryTrainer;


//    @GetMapping(value = "/Image/{id:.+}")
//    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
//        byte[] image = imageService.getImage(id);
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
//    }

        @GetMapping("/video/thumbnail/{courseId}/{date}/{fileName}")
        public ResponseEntity<byte[]> getImage(@PathVariable String courseId, @PathVariable String date,@PathVariable String fileName) throws IOException {
            File img = new File(path+courseId+"/"+date+"/"+fileName);
            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
        }



        @RequestMapping(value = "/course/upload/thumbnail/{courseId}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<String> uploadCourseThumbnail(@PathVariable String courseId,@RequestParam("thumbnail") MultipartFile thumbnail)
        {
            try {
                Optional<Course> courseOptional = repositoryCourse.findById(courseId);
                if (!courseOptional.isPresent())
                {
                    return new ResponseEntity<>("No such courseId exist", HttpStatus.BAD_REQUEST);
                }
                Course course=courseOptional.get();



                File convertFile2 = new File(path + courseId+ "/" + thumbnail.getOriginalFilename());


                convertFile2.createNewFile();

                FileOutputStream fout2 = new FileOutputStream(convertFile2);
                fout2.write(thumbnail.getBytes());
                fout2.close();
                //set course path
                course.setThumbnailPath("http://localhost:8081/course/thumbnail/"+courseId+"/"+ thumbnail.getOriginalFilename());

                Optional<TrainerModel> trainerModelOptional=repositoryTrainer.findById(course.getTrainerEmailId());
                TrainerModel trainerModel=trainerModelOptional.get();
                int j=0;
                for (int i=0;i<trainerModel.getCourses().size();i++)
                {
                    Course courseInList =trainerModel.getCourses().get(i);
                    if (courseInList.getId().equals(courseId))
                    {
                        trainerModel.getCourses().set(i,course);
                        repositoryTrainer.save(trainerModel);
                        j=1;
                        break;
                    }

                }
                if (j==0)
                {
                    return new ResponseEntity<>("Course id not matching in trainer",HttpStatus.BAD_REQUEST);
                }
                repositoryCourse.save(course);
                return new ResponseEntity<>("thumnail uploaded",HttpStatus.OK);



            }
            catch (Exception e) {
                e.printStackTrace();

                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }

        }

    @GetMapping("/course/thumbnail/{courseId}/{fileName}")
    public ResponseEntity<byte[]> getImageCourse(@PathVariable String courseId,@PathVariable String fileName) throws IOException {
        File img = new File(path+courseId+"/"+fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }


    @RequestMapping(value = "uploadTrainerProfilePicByUin/{uin}", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDp(@PathVariable Integer uin,@RequestParam("profilePic") MultipartFile profilePic) {
        try {


            TrainerModel trainer = repositoryTrainer.findByUin(uin);
            if (trainer == null) {
                return new ResponseEntity<>("Wrong uin entered", HttpStatus.BAD_REQUEST);
            }


            FileUtils.deleteDirectory(new File(path+"trainerProfilePic"+"/"+trainer.getId()));


            File makeDir = new File(path + "trainerProfilePic");
            boolean dirCreated = makeDir.mkdir();
             File makeDir2= new File(path+"trainerProfilePic/"+trainer.getId());
             boolean dirCreated2= makeDir2.mkdir();





            File convertFile2 = new File(path + "trainerProfilePic" + "/" + trainer.getId() +"/" +profilePic.getOriginalFilename());
            convertFile2.createNewFile();
            FileOutputStream fout2 = new FileOutputStream(convertFile2);
            fout2.write(profilePic.getBytes());
            fout2.close();
            trainer.setProfilePicPath("http://localhost:8081/trainer/profilePic/" + trainer.getId() +"/" +profilePic.getOriginalFilename());
            repositoryTrainer.save(trainer);
            return new ResponseEntity<>("profile pic uploaded successfully",HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/trainer/profilePic/{trainerId}/{fileName}")
    public ResponseEntity<byte[]> getTrainerDp(@PathVariable String trainerId,@PathVariable String fileName) throws IOException {
        File img = new File(path+"trainerProfilePic" + "/"+trainerId+"/"+fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }





}

