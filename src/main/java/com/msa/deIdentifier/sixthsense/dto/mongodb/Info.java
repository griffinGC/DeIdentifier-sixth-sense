package com.msa.deIdentifier.sixthsense.dto.mongodb;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Info {
    private String colName;
    private Summary summary;
    private int nullCount;
}
