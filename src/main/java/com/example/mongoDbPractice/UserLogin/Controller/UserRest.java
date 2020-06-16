package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Model.ReturnLoginUser;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import com.example.mongoDbPractice.UserLogin.Model.User;
import com.example.mongoDbPractice.common.utils.IdGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserRest {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private RepositoryUserMongoDb repository;


    @GetMapping("/getAllUsers")
    public List<User> getUser()
    {
//        return repository.findByName(user.getName());

        return repository.findAll();

    }

    @PostMapping("/saveUser")
    public ReturnLoginUser saveUser(@Valid @RequestBody User user)
    {

        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()>0)
        {
            return new ReturnLoginUser(false,"email already exists",null);
        }

        user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

       User savedUser= repository.save(user);
        return new ReturnLoginUser(true,"user saved",savedUser);
    }

    @GetMapping("/loginUser")
    public ReturnLoginUser loginUser(@Valid @RequestBody User user)
    {
        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()!=1)
        {
            return new ReturnLoginUser(false,"no such email exits",null);
        }
        if (!userList.get(0).getPassword().equals(user.getPassword()))
        {
            return new ReturnLoginUser(false,"password mismatch",null);

        }
        return new ReturnLoginUser(true,"users credentials matched",userList.get(0));

        }



}
