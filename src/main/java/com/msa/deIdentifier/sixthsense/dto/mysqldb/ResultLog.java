package com.msa.deIdentifier.sixthsense.dto.mysqldb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class ResultLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;
    private Date successDate;
    @Column(nullable = false)
    private String originLocation;
    private String resultTable;
    private Boolean isSucceed;
    private Date downloadDate;
    private Integer downloadCnt;
}
