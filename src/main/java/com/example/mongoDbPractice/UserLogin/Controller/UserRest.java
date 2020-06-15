package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import com.example.mongoDbPractice.UserLogin.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRest {

    @Autowired
    private RepositoryUserMongoDb repository;


    @GetMapping("/get")
    public List<User> getUser(@RequestBody User user)
    {
//        return repository.findByName(user.getName());

        return repository.findAll();

    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user)
    {

//
        return repository.save(user);
    }

    @GetMapping
    public String printHello()
    {
        return "<h1>Hello</h1>";
    }

}
