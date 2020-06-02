package com.msa.deIdentifier.sixthsense.dto.mongodb;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Summary {
    private final String dataType;
    private final String deIdentified;
    private final String prove;

}
