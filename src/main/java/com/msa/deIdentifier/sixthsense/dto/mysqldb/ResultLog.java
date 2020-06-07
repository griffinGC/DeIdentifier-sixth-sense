package com.msa.deIdentifier.sixthsense.dto.mysqldb;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class ResultLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String originLocation;
    private String resultLocation;
//    private String resultTable;
    private Boolean isSucceed;
    private Date downloadDate;
    private Integer downloadCnt;
    @Column(nullable = false)
    private String userName;
}
