package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Model.EmailFormat;
import com.example.mongoDbPractice.UserLogin.Model.ReturnLoginUser;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryOtp;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import com.example.mongoDbPractice.UserLogin.Model.User;
import com.example.mongoDbPractice.common.utils.IdGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserRest {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private RepositoryUserMongoDb repository;

    @Autowired
    private RepositoryOtp repositoryOtp;



    @PostMapping("/getAllUsers")
    public ResponseEntity<List<User>> getUser()
    {
        return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
    }

    @PostMapping("/saveUser/{otp}")
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user,@PathVariable String otp)
    {
        Optional<User> userOptional=repository.findById(user.getId());
        if (userOptional.isPresent())
        {
            return new ResponseEntity<>("email already exists",HttpStatus.BAD_REQUEST);
        }
        EmailFormat emailFormat = repositoryOtp.findByEmail(user.getId());
        if (emailFormat==null)
        {
            return new ResponseEntity<>("otp cant be verified",HttpStatus.BAD_REQUEST);
        }
        if (emailFormat.getOtp().equals(otp))
        {
            int uniqueUin=generateUin();
            while (repository.findByUin(uniqueUin)!=null)
            {
                uniqueUin=generateUin();
            }
            user.setUin(uniqueUin);
//            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));
            User savedUser= repository.save(user);
            repositoryOtp.delete(emailFormat);
            return new ResponseEntity<String>(String.valueOf(savedUser.getUin()),HttpStatus.OK);
        }
        return new ResponseEntity<>("otp mismatch",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/saveUser/googleFb")
    public ResponseEntity<String> saveUserGoogle(@Valid @RequestBody User user)
    {
        Optional<User> userOptional=repository.findById(user.getId());
        if (userOptional.isPresent())
        {
            return new ResponseEntity<>("email already exists",HttpStatus.BAD_REQUEST);
        }
        if (user.getFbVerified()!=null && user.getFbVerified())
        {
//            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            int uniqueUin=generateUin();
            while (repository.findByUin(uniqueUin)!=null)
            {
                uniqueUin=generateUin();
            }
            user.setUin(uniqueUin);

            User savedUser= repository.save(user);
            return new ResponseEntity<String>(String.valueOf(savedUser.getUin()),HttpStatus.OK);
        }
        if (user.getGoogleVerified()!=null && user.getGoogleVerified())
        {
//            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            int uniqueUin=generateUin();
            while (repository.findByUin(uniqueUin)!=null)
            {
                uniqueUin=generateUin();
            }
            user.setUin(uniqueUin);

            User savedUser= repository.save(user);
            return new ResponseEntity<String>(String.valueOf(savedUser.getUin()),HttpStatus.OK);
        }
        return new ResponseEntity<>("fb and google is false",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginUser/fbGoogle")
    public ResponseEntity<String> loginUserFbGoogle(@Valid @RequestBody User user)
    {
        Optional<User> userOptional=repository.findById(user.getId());
        if (!userOptional.isPresent())
        {
            return new ResponseEntity<>("no such email exits",HttpStatus.BAD_REQUEST);
        }
        if (userOptional.get().getGoogleVerified()==null && userOptional.get().getFbVerified()==null)
        {
            return new ResponseEntity<>("user not signed up via google or fb",HttpStatus.BAD_REQUEST);
        }
        if (user.getFbVerified()!=null && user.getFbVerified())
        {
            return new ResponseEntity<String>(String.valueOf(userOptional.get().getUin()),HttpStatus.OK);
        }
        if (user.getGoogleVerified()!=null && user.getGoogleVerified())
        {
            return new ResponseEntity<String>(String.valueOf(userOptional.get().getUin()),HttpStatus.OK);
        }
        return new ResponseEntity<>("fb google authentication failed",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody User user)
    {
        Optional<User> userOptional=repository.findById(user.getId());
        if (!userOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"no such email exits",null),HttpStatus.BAD_REQUEST);
        }
        if (!userOptional.get().getPassword().equals(user.getPassword()))
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"password mismatch",null),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ReturnLoginUser(true,"users credentials matched",userOptional.get().getUin()),HttpStatus.OK);
     }

    private int generateUin() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return number;

    }

    @PostMapping("/getByUin")
    public ResponseEntity<Object> getUserByUin(@RequestBody User user)
    {
        User userFound=repository.findByUin(user.getUin());
        if (userFound==null)
        {
            return new ResponseEntity<>("No such User exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userFound,HttpStatus.OK);
    }

//    @PostMapping("/deleteById")
//    public Boolean



}
