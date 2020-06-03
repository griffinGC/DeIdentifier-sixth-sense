package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import org.deidentifier.arx.ARXResult;

public interface DeidentifierService {
    ARXResult deidentification(SummaryData summaryData);
    ResultLog saveResult(ARXResult arxResult);
}
