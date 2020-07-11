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

    public Course_User_Model(String id, List<String> usersEnrolledIds) {
        this.id = id;
        this.usersEnrolledIds = usersEnrolledIds;
    }

    public Course_User_Model() {
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
