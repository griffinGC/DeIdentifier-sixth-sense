package com.msa.deIdentifier.sixthsense.mongoRepository;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryDataRepo extends MongoRepository<SummaryData, String> {

}
