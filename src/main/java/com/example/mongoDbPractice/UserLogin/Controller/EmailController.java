package com.example.mongoDbPractice.UserLogin.Controller;
import com.example.mongoDbPractice.UserLogin.Model.EmailFormat;
import com.example.mongoDbPractice.UserLogin.Repository.RepositoryOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EmailController {
    private String code;

    @Autowired
    private RepositoryOtp repositoryOtp;

    public EmailController(String code) {
        this.code = code;
    }

    public EmailController() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @PostMapping(value = "/sendmail")
    public String sendEmail(@RequestBody EmailFormat emailFormat)
    {
        try {
            String result=sendmail(emailFormat);
//            Optional<EmailFormat> savedOtp = repositoryOtp.findById(emailFormat.getEmail());

            EmailFormat savedOtp=repositoryOtp.findByEmail(emailFormat.getEmail());
            if (savedOtp!=null)
            {
                repositoryOtp.delete(savedOtp);

            }
            repositoryOtp.save(new EmailFormat(emailFormat.getEmail(),result));
//            repositoryOtp.save(new EmailFormat(emailFormat.getEmail(),String.valueOf(1234)));


            return "success";
        }
        catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        return "Emails not sent successfully";
    }

    private String sendmail(EmailFormat emailFormat) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("fitindia123456@gmail.com", "password@1234");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("rishabhkohli13@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailFormat.getEmail()));
        msg.setSubject("OTP");
        msg.setContent("we got this", "text/html");
        msg.setSentDate(new Date());

        Random rand = new Random();
        String id=String.format("%04d", rand.nextInt(10000));
        System.out.println(id);
        code=id;
//        setCode(id);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<H2>Your OTP for email verification is: "+id+"</H2>", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
//        MimeBodyPart attachPart = new MimeBodyPart();

//        attachPart.attachFile("/home/rishabh.kohli/Desktop/resume.png");
//        attachPart.attachFile("");
//        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
        return id;
    }

    @PostMapping("code/{code}")
    public Boolean checkCode(@PathVariable String code){





        System.out.println(this.code);
        System.out.println(code);
        System.out.println(code.getClass().getSimpleName());
        System.out.println(this.code.getClass().getSimpleName());
        if (code.equals(this.code))
        {
            return true;
        }
        else
            return false;
    }
}
