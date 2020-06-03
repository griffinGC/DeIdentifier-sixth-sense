package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ResultService {
    List<ResultLog> getResultLogAll();
    ResultLog createResultLog(ResultLog resultLog);
    ResultLog getResultLogByFileName(String fileName);
}
