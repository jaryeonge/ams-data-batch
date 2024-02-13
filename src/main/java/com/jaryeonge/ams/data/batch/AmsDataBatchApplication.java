package com.jaryeonge.ams.data.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class AmsDataBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsDataBatchApplication.class, args);
    }

}
