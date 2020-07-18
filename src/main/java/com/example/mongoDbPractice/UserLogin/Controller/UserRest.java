package com.example.mongoDbPractice.UserLogin.Controller;

import com.example.mongoDbPractice.UserLogin.Model.*;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryOtp;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryUserMongoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserRest {
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private RepositoryUserMongoDb repositoryUser;

    @Autowired
    private RepositoryOtp repositoryOtp;



    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getUser(@RequestHeader("headerKey") String headerKey)
    {

        if(StringUtils.isEmpty(headerKey) || !headerKey.equals("98f88a00-152a-4627-8157-3814c754c035")) {
            throw new IllegalArgumentException("Header - secret key is empty or wrong");
        }
        return new ResponseEntity<>(repositoryUser.findAll(),HttpStatus.OK);
    }

    @PostMapping("/saveUser/{otp}")
    public ResponseEntity<ReturnLoginUser> saveUser(@Valid @RequestBody User user,@PathVariable String otp)
    {
        Optional<User> userOptional= repositoryUser.findById(user.getId());
        if (userOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"email already exists",null),HttpStatus.BAD_REQUEST);
        }
        EmailFormat emailFormat = repositoryOtp.findByEmail(user.getId());
        if (emailFormat==null)
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"otp cant be verified",null),HttpStatus.BAD_REQUEST);
        }
        if (emailFormat.getOtp().equals(otp))
        {
            int uniqueUin=generateUin();
            while (repositoryUser.findByUin(uniqueUin)!=null)
            {
                uniqueUin=generateUin();
            }
            user.setUin(uniqueUin);
//            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));
            User savedUser= repositoryUser.save(user);
            repositoryOtp.delete(emailFormat);
            return new ResponseEntity<>(new ReturnLoginUser(true,"Successfully saved",savedUser.getUin()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ReturnLoginUser(false,"otp mismatch",null),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/saveUser/googleFb")
    public ResponseEntity<ReturnLoginUser> saveUserGoogle(@Valid @RequestBody User user)
    {
        Optional<User> userOptional= repositoryUser.findById(user.getId());
        if (userOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"email already exists! Please login",null),HttpStatus.BAD_REQUEST);
        }
        if (user.getFbVerified()!=null && user.getFbVerified())
        {
//            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            int uniqueUin=generateUin();
            while (repositoryUser.findByUin(uniqueUin)!=null)
            {
                uniqueUin=generateUin();
            }
            user.setUin(uniqueUin);

            User savedUser= repositoryUser.save(user);
            return new ResponseEntity<>(new ReturnLoginUser(true,"Saved successful",savedUser.getUin()),HttpStatus.OK);
        }
        if (user.getGoogleVerified()!=null && user.getGoogleVerified())
        {
//            user.setId(IdGenerator.generateId(user.getFirstName(),user.getLastName()));

            int uniqueUin=generateUin();
            while (repositoryUser.findByUin(uniqueUin)!=null)
            {
                uniqueUin=generateUin();
            }
            user.setUin(uniqueUin);

            User savedUser= repositoryUser.save(user);
            return new ResponseEntity<>(new ReturnLoginUser(true,"Saved successful",savedUser.getUin()),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ReturnLoginUser(false,"fb and google is false",null),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginUser/fbGoogle")
    public ResponseEntity<ReturnLoginUser> loginUserFbGoogle(@Valid @RequestBody User user)
    {
        Optional<User> userOptional= repositoryUser.findById(user.getId());
        if (!userOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"no such email exits",null),HttpStatus.BAD_REQUEST);
        }
        if (userOptional.get().getGoogleVerified()==null && userOptional.get().getFbVerified()==null)
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"user not signed up via google or fb",null),HttpStatus.BAD_REQUEST);
        }
        if (user.getFbVerified()!=null && user.getFbVerified())
        {
            return new ResponseEntity<>(new ReturnLoginUser(true,"User verified",(userOptional.get().getUin())),HttpStatus.OK);
        }
        if (user.getGoogleVerified()!=null && user.getGoogleVerified())
        {
            return new ResponseEntity<>(new ReturnLoginUser(true,"user verified",(userOptional.get().getUin())),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ReturnLoginUser(false,"fb google authentication failed",null),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<ReturnLoginUser> loginUser(@Valid @RequestBody User user)
    {
        Optional<User> userOptional= repositoryUser.findById(user.getId());
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

    @GetMapping("/getByUin/{uin}")
    public ResponseEntity<Object> getUserByUin(@PathVariable int uin,@RequestHeader("headerKey") String headerKey)
    {

        if(StringUtils.isEmpty(headerKey) || !headerKey.equals("98f88a00-152a-4627-8157-3814c754c035")) {
            throw new IllegalArgumentException("Header - secret key is empty or wrong");
        }

        User userFound= repositoryUser.findByUin(uin);
        if (userFound==null)
        {
            return new ResponseEntity<>("No such User exists", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userFound,HttpStatus.OK);
    }

    @PostMapping("/checkIfFbOrGoogle")
    public ResponseEntity<ReturnModelCheckFbGoogle> checkFb(@RequestBody CheckIfFB_Google checkIfFB_google)
    {
        Optional<User> userOptional= repositoryUser.findById(checkIfFB_google.getId());

        if (!userOptional.isPresent())
        {
            return new ResponseEntity<>(new ReturnModelCheckFbGoogle("3","No such email id registered",null),HttpStatus.OK);
        }
        User user=userOptional.get();

        if (checkIfFB_google.getOath().equals("f"))
        {
            if (user.getFbVerified()!=null &&user.getFbVerified())
            {
                return new ResponseEntity<>(new ReturnModelCheckFbGoogle("1","User was registered by Fb" ,user.getUin()),HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(new ReturnModelCheckFbGoogle("2","User isn't registered through facebook",null),HttpStatus.OK);
            }

        }
        if (checkIfFB_google.getOath().equals("g"))
        {
            if (user.getGoogleVerified()!=null && user.getGoogleVerified())
            {
                return new ResponseEntity<>(new ReturnModelCheckFbGoogle("1","User was registered by Google" ,user.getUin()),HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(new ReturnModelCheckFbGoogle("2","User isn't registered through Google",null),HttpStatus.OK);
            }

        }


        return new ResponseEntity<>(new ReturnModelCheckFbGoogle("2","Wrong auth token",null),HttpStatus.OK);
    }

    @PostMapping("/updateUserByUin")
    public ResponseEntity<ReturnLoginUser> updateUserByUin(@RequestBody User userNew)
    {
        if (userNew.getUin()==null)
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"uin can't be empty",null),HttpStatus.BAD_REQUEST);
        }
        User userSaved = repositoryUser.findByUin(userNew.getUin());
        if (userSaved==null)
        {
            return new ResponseEntity<>(new ReturnLoginUser(false,"No such uin registered",null),HttpStatus.BAD_REQUEST);
        }
        if (userNew.getPassword()!=null)
        {
            userSaved.setPassword(userNew.getPassword());
        }
        if (userNew.getDOB()!=null)
        {
            userSaved.setDOB(userNew.getDOB());
        }
        if (userNew.getFirstName()!=null)
        {
            userSaved.setFirstName(userNew.getFirstName());
        }
        if (userNew.getLastName()!=null)
        {
            userSaved.setLastName(userNew.getLastName());
        }
        if (userNew.getGender()!=null)
        {
            userSaved.setGender(userNew.getGender());
        }
        if (userNew.getPhoneNumber()!=null)
        {
            userSaved.setPhoneNumber(userNew.getPhoneNumber());
        }
        repositoryUser.save(userSaved);
        return new ResponseEntity<>(new ReturnLoginUser(true,"updated successfully",userSaved.getUin()),HttpStatus.OK);

    }






}
