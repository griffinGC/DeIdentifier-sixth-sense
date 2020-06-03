package com.msa.deIdentifier.sixthsense.mysqlRepository;

import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultLogRepo extends JpaRepository<ResultLog, Long> {
    ResultLog findByFileName(String fileName);
}
