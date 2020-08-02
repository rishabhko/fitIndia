package com.example.mongoDbPractice.Leads.Repository;

import com.example.mongoDbPractice.Leads.Model.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryLead extends MongoRepository<Lead,Integer> {

    List<Lead> findByMobileNumber(String number);
    List<Lead> findByCreationDate(LocalDate date);
}
