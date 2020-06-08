package com.msa.deIdentifier.sixthsense.controller;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import com.msa.deIdentifier.sixthsense.service.ResultService;
import com.msa.deIdentifier.sixthsense.service.SummaryService;
import com.msa.deIdentifier.sixthsense.temp.DeIdentifierTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class DtoController {
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private ResultService resultService;

    @GetMapping("/test")
    public String testDB(){
        return "test DB!";
    }
    @GetMapping("/getMongoAll")
    public List<SummaryData> getAllMongo(){
        List<SummaryData> summaryDataList = summaryService.getSummaryAll();
        return summaryDataList;
    }

    @GetMapping("/getSummary/{fileName}")
    public SummaryData getSummaryDataByFileName(@PathVariable String fileName){
        SummaryData summaryData = summaryService.getSummaryByFileName(fileName);
        return summaryData;
    }

    @GetMapping("/getMysqlAll")
    public List<ResultLog> getAllMysql(){
        List<ResultLog> resultLogList = resultService.getResultLogAll();
        return resultLogList;
    }

    @GetMapping("/getLog/{fileName}")
    public ResultLog getResultLogByFileName(@PathVariable String fileName){
        ResultLog resultLog = resultService.getResultLogByFileName(fileName);
        return resultLog;
    }

    @PostMapping("/createLog")
    public ResultLog createResultLog(@RequestBody ResultLog resultLog){
        ResultLog checkResult = resultService.createResultLog(resultLog);
        return checkResult;
    }

    @GetMapping("/byUserName/{userName}")
    public List<ResultLog> getByUserName(@PathVariable String userName){
        return resultService.getResultLogByUserName(userName);
    }
}
