package com.msa.deIdentifier.sixthsense.dto.mysqldb;

import lombok.Data;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
public class ResultLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(nullable = false)
    private final String fileName;
    private final Date successDate;
    @Column(nullable = false)
    private final String originLocation;
    private final String resultTable;
    private final Boolean isSucceed;
    private final Date downloadDate;
    private final Integer downloadCnt;
}
