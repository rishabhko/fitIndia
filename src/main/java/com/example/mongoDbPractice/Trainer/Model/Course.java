package com.example.mongoDbPractice.Trainer.Model;

import com.example.mongoDbPractice.UserLogin.Model.User;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;



@Document(collection = "Course")
public class Course implements Comparable<Course> {

    @Id
    String id;

//    @NotNull
    String trainerEmailId;
    String name;
    String description;
    BigDecimal fees;
    String thumbnailPath;
    Integer uin;
    String category;
    Boolean valid;
    LocalDate creationDate;
    LocalDate updationData;

    List<Video> videos;
//    List<User> usersEnrolled;


    public Course(String id, String trainerEmailId, String name, String description, BigDecimal fees, String thumbnailPath, Integer uin, String category, Boolean valid, LocalDate creationDate, LocalDate updationData, List<Video> videos) {
        this.id = id;
        this.trainerEmailId = trainerEmailId;
        this.name = name;
        this.description = description;
        this.fees = fees;
        this.thumbnailPath = thumbnailPath;
        this.uin = uin;
        this.category = category;
        this.valid = valid;
        this.creationDate = creationDate;
        this.updationData = updationData;
        this.videos = videos;
//        this.usersEnrolled = usersEnrolled;
    }

    public Course() {
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdationData() {
        return updationData;
    }

    public void setUpdationData(LocalDate updationData) {
        this.updationData = updationData;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getUin() {
        return uin;
    }

    public void setUin(Integer uin) {
        this.uin = uin;
    }

//    public List<User> getUsersEnrolled() {
//        return usersEnrolled;
//    }
//
//    public void setUsersEnrolled(List<User> usersEnrolled) {
//        this.usersEnrolled = usersEnrolled;
//    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerEmailId() {
        return trainerEmailId;
    }

    public void setTrainerEmailId(String trainerEmailId) {
        this.trainerEmailId = trainerEmailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public int compareTo(Course o) {
        return getCreationDate().compareTo(o.getCreationDate());
    }
}
