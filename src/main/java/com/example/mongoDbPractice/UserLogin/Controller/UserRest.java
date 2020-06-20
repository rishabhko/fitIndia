package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Model.EmailFormat;
import com.example.mongoDbPractice.UserLogin.Model.ReturnLoginUser;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryOtp;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import com.example.mongoDbPractice.UserLogin.Model.User;
import com.example.mongoDbPractice.common.utils.IdGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserRest {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private RepositoryUserMongoDb repository;

    @Autowired
    private RepositoryOtp repositoryOtp;



    @GetMapping("/getAllUsers")
    public List<User> getUser()
    {
//        return repository.findByName(user.getName());

        return repository.findAll();

    }


    @PostMapping("/saveUser/{otp}")
    public ReturnLoginUser saveUser(@Valid @RequestBody User user,@PathVariable String otp)
    {

        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()>0)
        {
            return new ReturnLoginUser(false,"email already exists",null);
        }


        EmailFormat emailFormat = repositoryOtp.findByEmail(user.getEmailId());
        if (emailFormat==null)
        {
            return new ReturnLoginUser(false,"otp cant be verified",null);

        }
        if (emailFormat.getOtp().equals(otp))
        {
            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            User savedUser= repository.save(user);
            repositoryOtp.delete(emailFormat);
            return new ReturnLoginUser(true,"user saved",savedUser);

        }

        return new ReturnLoginUser(false,"otp mismatch",null);




    }

    @PostMapping("/saveUser/googleFb")
    public ReturnLoginUser saveUserGoogle(@Valid @RequestBody User user)
    {
        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()>0)
        {
            return new ReturnLoginUser(false,"email already exists",null);
        }
        if (user.getFbVerified()!=null && user.getFbVerified())
        {
            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            User savedUser= repository.save(user);
            return new ReturnLoginUser(true,"user saved",savedUser);
        }
        if (user.getGoogleVerified()!=null && user.getGoogleVerified())
        {
            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            User savedUser= repository.save(user);
            return new ReturnLoginUser(true,"user saved",savedUser);
        }
        return new ReturnLoginUser(false,"fb and google is false",null);
    }

    @GetMapping("/loginUser/fbGoogle")
    public ReturnLoginUser loginUserFbGoogle(@Valid @RequestBody User user)
    {
        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()!=1)
        {
            return new ReturnLoginUser(false,"no such email exits",null);
        }
        if (userList.get(0).getGoogleVerified()==null && userList.get(0).getFbVerified()==null)
        {
            return new ReturnLoginUser(false,"user not signed up via google or fb",null);

        }
        if (user.getFbVerified()!=null && user.getFbVerified())
        {
            return new ReturnLoginUser(true,"users credentials matched",userList.get(0));
        }
        if (user.getGoogleVerified()!=null && user.getGoogleVerified())
        {
            return new ReturnLoginUser(true,"users credentials matched",userList.get(0));
        }

        return new ReturnLoginUser(false,"fb google authentication failed",null);

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
