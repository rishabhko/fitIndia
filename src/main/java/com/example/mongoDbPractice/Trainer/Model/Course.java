package com.example.mongoDbPractice.Trainer.Model;

import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Document(collection = "Course")
public class Course {

    @Id
    String id;

//    @NotNull
    String trainerEmailId;
    String name;
    String description;
    BigDecimal fees;

    List<Video> videos;
    List<User> usersEnrolled;

    public Course(String id, String trainerEmailId, String name, String description, BigDecimal fees, List<Video> videos, List<User> usersEnrolled) {
        this.id = id;
        this.trainerEmailId = trainerEmailId;
        this.name = name;
        this.description = description;
        this.fees = fees;
        this.videos = videos;
        this.usersEnrolled = usersEnrolled;
    }

    public Course() {
    }

    public List<User> getUsersEnrolled() {
        return usersEnrolled;
    }

    public void setUsersEnrolled(List<User> usersEnrolled) {
        this.usersEnrolled = usersEnrolled;
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
}
