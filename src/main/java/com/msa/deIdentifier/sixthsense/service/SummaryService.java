package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;

import java.util.List;

public interface SummaryService {
    List<SummaryData> getSummaryAll();
    SummaryData getSummaryByFileName(String fileName);
}
