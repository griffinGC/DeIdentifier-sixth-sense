package com.msa.deIdentifier.sixthsense.controller;

import com.msa.deIdentifier.sixthsense.service.DeidentifierService;
import com.msa.deIdentifier.sixthsense.temp.DeIdentifierTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class DeIdentifierController {
    @Autowired
    DeidentifierService deidentifierService;

    @GetMapping("/testDeidentifier")
    public String testDeidentifier() throws IOException, SQLException {
        DeIdentifierTest test = new DeIdentifierTest();
        test.testDeIdentifier();
        return "test1 end!!!";
    }
}
