package com.msa.deIdentifier.sixthsense.dto.mongodb;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Mulcamp")
@Data
@RequiredArgsConstructor
public class SummaryData {
    private final String _id;
    private final String fileName;
//    private final List<Summary> info;
    private final Info[] info;
}

