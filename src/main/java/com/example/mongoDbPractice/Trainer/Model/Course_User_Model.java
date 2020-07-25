package com.example.mongoDbPractice.Trainer.Model;

import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Course_User")
public class Course_User_Model {

    @Id
    String id;     //same as courseId

    List<String> usersEnrolledIds;
    List<Integer> usersEnrolledUins;

    public Course_User_Model(String id, List<String> usersEnrolledIds, List<Integer> usersEnrolledUins) {
        this.id = id;
        this.usersEnrolledIds = usersEnrolledIds;
        this.usersEnrolledUins = usersEnrolledUins;
    }

    public Course_User_Model() {
    }

    public List<Integer> getUsersEnrolledUins() {
        return usersEnrolledUins;
    }

    public void setUsersEnrolledUins(List<Integer> usersEnrolledUins) {
        this.usersEnrolledUins = usersEnrolledUins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsersEnrolledIds() {
        return usersEnrolledIds;
    }

    public void setUsersEnrolledIds(List<String> usersEnrolledIds) {
        this.usersEnrolledIds = usersEnrolledIds;
        ;
    }
}
