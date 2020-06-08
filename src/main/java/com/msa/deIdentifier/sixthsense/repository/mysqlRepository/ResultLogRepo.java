package com.msa.deIdentifier.sixthsense.repository.mysqlRepository;

import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultLogRepo extends JpaRepository<ResultLog, Long> {
    ResultLog findByFileName(String fileName);
    List<ResultLog> findByUserName(String userName);
}
