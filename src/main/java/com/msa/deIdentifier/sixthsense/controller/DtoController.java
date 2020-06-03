package com.msa.deIdentifier.sixthsense.controller;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import com.msa.deIdentifier.sixthsense.service.ResultService;
import com.msa.deIdentifier.sixthsense.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class DtoController {

    @GetMapping("/test")
    public String testDB(){
        return "test DB!";
    }
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private ResultService resultService;

    @GetMapping("/getMongoAll")
    public List<SummaryData> getAllMongo(){
        List<SummaryData> summaryDataList = summaryService.getSummaryAll();
        return summaryDataList;
    }
    @GetMapping("/getMysqlAll")
    public List<ResultLog> getAllMysql(){
        List<ResultLog> resultLogList = resultService.getResultLogAll();
        return resultLogList;
    }

}
