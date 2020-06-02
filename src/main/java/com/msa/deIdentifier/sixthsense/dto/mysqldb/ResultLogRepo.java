package com.msa.deIdentifier.sixthsense.dto.mysqldb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultLogRepo extends JpaRepository<ResultLog, Long> {
//public interface ResultLogRepo extends CrudRepository<ResultLog, Long>{
}
