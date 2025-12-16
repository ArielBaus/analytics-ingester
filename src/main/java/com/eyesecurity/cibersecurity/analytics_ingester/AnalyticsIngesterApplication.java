package com.eyesecurity.cibersecurity.analytics_ingester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableResilientMethods
public class AnalyticsIngesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsIngesterApplication.class, args);
	}

}
