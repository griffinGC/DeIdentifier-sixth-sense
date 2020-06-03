package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import com.msa.deIdentifier.sixthsense.repository.mongoRepository.SummaryDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableMongoRepositories(basePackageClasses = SummaryDataRepo.class)
@Service
public class SummaryServiceImpl implements SummaryService {
    @Autowired
    SummaryDataRepo summaryDataRepo;

    @Override
    public List<SummaryData> getSummaryAll() {
        List<SummaryData> summaryDataList = summaryDataRepo.findAll();
        return summaryDataList;
    }

    @Override
    public SummaryData getSummaryByFileName(String fileName) {
        SummaryData summaryData = summaryDataRepo.findByFileName(fileName);
        return summaryData;
    }
}
