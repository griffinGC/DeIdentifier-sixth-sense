package com.msa.deIdentifier.sixthsense.repository.mongoRepository;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import org.springframework.data.mongodb.repository.MongoRepository;

//@Repository
public interface SummaryDataRepo extends MongoRepository<SummaryData, String> {
    SummaryData findByFileName(String fileName);
}
