package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Model.EmailFormat;
import com.example.mongoDbPractice.UserLogin.Model.ReturnLoginUser;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryOtp;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import com.example.mongoDbPractice.UserLogin.Model.User;
import com.example.mongoDbPractice.common.exception.EntityAlreadyExistsException;
import com.example.mongoDbPractice.common.exception.ErrorObject;
import com.example.mongoDbPractice.common.exception.OtpNotVerifiedException;
import com.example.mongoDbPractice.common.utils.IdGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserRest {
   //private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private RepositoryUserMongoDb repository;

    @Autowired
    private RepositoryOtp repositoryOtp;

    @Autowired
    User user;

    @GetMapping("/getAllUsers")
    public List<User> getUser()
    {
//        return repository.findByName(user.getName());

        return repository.findAll();

    }


    @PostMapping("/saveUser/{otp}")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user,@PathVariable String otp, BindingResult errorList)
    {
        if(errorList.hasErrors())
        {
            return handleErrors(errorList);
        }
        List<User> userList=repository.findByEmailId(user.getEmailId());
        if (userList.size()>0)
        {
            //return new ReturnLoginUser(false,"email already exists",null);
            return new ResponseEntity<>(new ReturnLoginUser(false,"Email already Exists",null),HttpStatus.BAD_REQUEST);
        }


        EmailFormat emailFormat = repositoryOtp.findByEmail(user.getEmailId());
        if (emailFormat==null)
        {
            //throw new OtpNotVerifiedException("Otp Not Verified");
            //return new ReturnLoginUser(false,"otp cant be verified",null);
            return new ResponseEntity<>(new ReturnLoginUser(false,"otp cant be verified",null),HttpStatus.BAD_REQUEST);

        }
        if (emailFormat.getOtp().equals(otp))
        {
            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            User savedUser= repository.save(user);
            repositoryOtp.delete(emailFormat);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
            //return new ReturnLoginUser(true,"user saved",savedUser);

        }
        //throw new OtpNotVerifiedException("Otp mismatch");
        //return new ReturnLoginUser(false,"otp mismatch",null);
        return new ResponseEntity<>(new ReturnLoginUser(false,"otp mismatch",null),HttpStatus.BAD_REQUEST);



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

    private ResponseEntity<Object> handleErrors(BindingResult errorList) {

        return new ResponseEntity<>(errorList.getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
