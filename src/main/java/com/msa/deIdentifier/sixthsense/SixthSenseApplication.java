package com.msa.deIdentifier.sixthsense;

import com.msa.deIdentifier.sixthsense.dto.mongodb.SummaryDataRepo;
import com.msa.deIdentifier.sixthsense.dto.mysqldb.ResultLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
@SpringBootApplication
public class SixthSenseApplication {


	public static void main(String[] args) {
		SpringApplication.run(SixthSenseApplication.class, args);
	}

}
