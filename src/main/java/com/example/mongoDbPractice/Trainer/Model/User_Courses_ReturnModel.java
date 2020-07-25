package com.example.mongoDbPractice.Trainer.Model;

public class User_Courses_ReturnModel {

    Integer courseUin;
    Integer trainerUin;
    String courseThumbnalPath;
    String trainerName;
    String courseDescription;
    String courseName;

    public User_Courses_ReturnModel(Integer courseUin, Integer trainerUin, String courseThumbnalPath, String trainerName, String courseDescription, String courseName) {
        this.courseUin = courseUin;
        this.trainerUin = trainerUin;
        this.courseThumbnalPath = courseThumbnalPath;
        this.trainerName = trainerName;
        this.courseDescription = courseDescription;
        this.courseName = courseName;
    }

    public User_Courses_ReturnModel() {
    }

    public Integer getCourseUin() {
        return courseUin;
    }

    public void setCourseUin(Integer courseUin) {
        this.courseUin = courseUin;
    }

    public Integer getTrainerUin() {
        return trainerUin;
    }

    public void setTrainerUin(Integer trainerUin) {
        this.trainerUin = trainerUin;
    }

    public String getCourseThumbnalPath() {
        return courseThumbnalPath;
    }

    public void setCourseThumbnalPath(String courseThumbnalPath) {
        this.courseThumbnalPath = courseThumbnalPath;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
