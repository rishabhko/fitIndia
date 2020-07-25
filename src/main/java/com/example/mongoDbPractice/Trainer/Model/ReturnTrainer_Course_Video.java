package com.example.mongoDbPractice.Trainer.Model;

import java.util.List;

public class ReturnTrainer_Course_Video {
    String trainerName;
    String[] certificatesTrainer;
    Integer trainerUin;
    String trainerProfilePicPath;
    //    Integer courseUin;
    Course course;
//    List<Video> videoUinList;


    public ReturnTrainer_Course_Video(String trainerName, String[] certificatesTrainer, Integer trainerUin, String trainerProfilePicPath, Course course) {
        this.trainerName = trainerName;
        this.certificatesTrainer = certificatesTrainer;
        this.trainerUin = trainerUin;
        this.trainerProfilePicPath = trainerProfilePicPath;
        this.course = course;
    }

    public ReturnTrainer_Course_Video() {
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String[] getCertificatesTrainer() {
        return certificatesTrainer;
    }

    public void setCertificatesTrainer(String[] certificatesTrainer) {
        this.certificatesTrainer = certificatesTrainer;
    }

    public Integer getTrainerUin() {
        return trainerUin;
    }

    public void setTrainerUin(Integer trainerUin) {
        this.trainerUin = trainerUin;
    }

    public String getTrainerProfilePicPath() {
        return trainerProfilePicPath;
    }

    public void setTrainerProfilePicPath(String trainerProfilePicPath) {
        this.trainerProfilePicPath = trainerProfilePicPath;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}