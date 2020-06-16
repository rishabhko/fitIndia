package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import com.example.mongoDbPractice.UserLogin.Model.User;
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
    public List<User> getUser( @RequestBody User user)
    {
//        return repository.findByName(user.getName());

        return repository.findAll();

    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @RequestBody User user)
    {

        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()>0)
        {
            return "Account with email exists";
        }

        repository.save(user);
        return "saved";
    }

    @GetMapping("/loginUser")
    public String loginUser(@Valid @RequestBody User user)
    {
        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()!=1)
        {
            return "No such email exist";
        }
        if (!userList.get(0).getPassword().equals(user.getPassword()))
        {
            return "Password mismatch";

        }
        return "Verified";

        }


}
