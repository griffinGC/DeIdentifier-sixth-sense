package com.msa.deIdentifier.sixthsense.service;

import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLog;
import com.msa.deIdentifier.sixthsense.repository.mysqlRepository.ResultLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableJpaRepositories(basePackageClasses = ResultLogRepo.class)
@Service
public class ResultServiceImpl implements ResultService{
    @Autowired
    ResultLogRepo resultLogRepo;
    @Override
    public List<ResultLog> getResultLogAll() {
        List<ResultLog> logList = resultLogRepo.findAll();
        return logList;
    }

    @Override
    public ResultLog createResultLog(ResultLog resultLog) {
        ResultLog resultLog1 = resultLogRepo.save(resultLog);
        System.out.println("저장한 원본 파일 이름 : " + resultLog1.getFileName());
        System.out.println("저장한 id 이름 : " + resultLog1.getId());
        System.out.println("저장한 시각 : " + resultLog1.getCreatedDate());
        return resultLog1;
    }

    @Override
    public ResultLog getResultLogByFileName(String fileName) {
        ResultLog resultLog = resultLogRepo.findByFileName(fileName);

        return resultLog;
    }
}
