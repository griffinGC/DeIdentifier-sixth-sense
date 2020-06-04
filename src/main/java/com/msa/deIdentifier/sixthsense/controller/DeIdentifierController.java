package com.msa.deIdentifier.sixthsense.controller;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import com.msa.deIdentifier.sixthsense.service.DeidentifierService;
import com.msa.deIdentifier.sixthsense.service.ResultService;
import com.msa.deIdentifier.sixthsense.service.SummaryService;
import com.msa.deIdentifier.sixthsense.temp.DeIdentifierTest;
import org.deidentifier.arx.ARXResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class DeIdentifierController {
    @Autowired
    DeidentifierService deidentifierService;

    @Autowired
    SummaryService summaryService;

    @GetMapping("/testDeidentifier")
    public String testDeidentifier() throws IOException, SQLException {
        DeIdentifierTest test = new DeIdentifierTest();
        test.testDeIdentifier();
        return "test1 end!!!";
    }

    @GetMapping("/deidentifier/{fileName}")
    public ResultLog testDeidentifier(@PathVariable String fileName) throws IOException, SQLException {
        SummaryData summaryData = summaryService.getSummaryByFileName(fileName);
        ResultLog resultLog = deidentifierService.deidentification(summaryData);
//        ResultLog resultLog = deidentifierService.saveResult(arxResult);

        return resultLog;
    }
}
