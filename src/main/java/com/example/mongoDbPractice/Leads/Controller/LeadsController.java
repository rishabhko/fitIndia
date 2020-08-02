package com.example.mongoDbPractice.Leads.Controller;

import com.example.mongoDbPractice.Leads.Model.Lead;
import com.example.mongoDbPractice.Leads.Repository.RepositoryLead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LeadsController {
    @Autowired
    private RepositoryLead repositoryLead;

    @PostMapping("/saveLead")
    public ResponseEntity<String> saveLead(@RequestBody Lead lead)
    {

        int uniqueId = generateId();
        while (repositoryLead.findById(uniqueId).isPresent()) {
            uniqueId = generateId();
        }
        lead.setId(uniqueId);
        lead.setCreationDate(LocalDate.now());
        repositoryLead.save(lead);
        return new ResponseEntity<>("saved", HttpStatus.OK);
    }

    @GetMapping("/getAllLeads")
    private ResponseEntity<List<Lead>> getAllLeads()
    {
        return new ResponseEntity<>(repositoryLead.findAll(),HttpStatus.OK);
    }


    private int generateId() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return number;

    }




}
