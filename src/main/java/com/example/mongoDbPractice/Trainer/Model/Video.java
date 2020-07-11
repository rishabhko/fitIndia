package com.example.mongoDbPractice.Trainer.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Video")
public class Video {

    @Id
    String id;
    String courseId;
    String name;
    String date;
    String thumbnailPath;
    String description;
    String title;
    String videoPath;


    public Video(String id, String courseId, String name, String date, String thumbnailPath, String description, String title, String videoPath) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.date = date;
        this.thumbnailPath = thumbnailPath;
        this.description = description;
        this.title = title;
        this.videoPath = videoPath;
    }

    public Video() {
    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
