package com.msa.deIdentifier.sixthsense.dto.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryDataRepo extends MongoRepository<SummaryData, String> {

}
