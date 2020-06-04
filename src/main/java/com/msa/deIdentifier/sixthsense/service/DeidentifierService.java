package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import org.deidentifier.arx.ARXResult;

import java.io.IOException;
import java.sql.SQLException;


public interface DeidentifierService {
    ResultLog deidentification(SummaryData summaryData) throws IOException, SQLException;
    ResultLog saveResult(ARXResult arxResult);
}
