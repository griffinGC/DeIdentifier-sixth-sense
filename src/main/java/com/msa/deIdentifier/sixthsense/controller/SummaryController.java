package com.msa.deIdentifier.sixthsense.controller;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryDataRepo;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLogRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@EnableMongoRepositories(basePackages = "com.msa.deIdentifier.sixthsense.dto.mongodb")
@EnableJpaRepositories(basePackages = "com.msa.deIdentifier.sixthsense.dto.mysqldb")
@RestController
public class SummaryController {
    @Autowired
    SummaryDataRepo summaryDataRepo;
    @Autowired
    private ResultLogRepo resultLogRepo;

    @GetMapping("/test")
    public String testDB(){
        return "test DB!";
    }

    @GetMapping("/getMongoDB")
    public List<SummaryData> testSummary(){
        List<SummaryData> summaryData = summaryDataRepo.findAll();
        return summaryData;
    }

//    @GetMapping("/getMysqlDB")
//    public List<ResultLog> testResultLog(){
//        List<ResultLog> data = resultLogRepo.findAll();
//
//        return data;
//    }
}
