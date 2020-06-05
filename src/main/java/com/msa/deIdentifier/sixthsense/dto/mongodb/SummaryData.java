package com.msa.deIdentifier.sixthsense.dto.mongodb;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "SummaryData")
@Data
@RequiredArgsConstructor
public class SummaryData {
    @Id
    private final String _id;
    private final String userName;
    private final String fileName;
    private final String originLocation;
    private final List<Info> info;
//    private final Info[] info;
}

