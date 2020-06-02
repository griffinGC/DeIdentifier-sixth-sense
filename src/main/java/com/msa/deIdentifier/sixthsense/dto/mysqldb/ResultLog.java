package com.msa.deIdentifier.sixthsense.dto.mysqldb;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
public class ResultLog {

    @Id
    private final Long id;

    private final String fileName;
    private final Date successDate;
    private final String originLocation;
    private final String resultTable;
    private final Boolean isSucceed;
    private final Date downloadDate;
    private final Integer downloadCnt;
}
