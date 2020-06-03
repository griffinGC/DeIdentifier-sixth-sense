package com.msa.deIdentifier.sixthsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
@EnableJpaAuditing
@SpringBootApplication
public class SixthSenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SixthSenseApplication.class, args);
	}

}
