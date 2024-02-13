package com.jaryeonge.ams.data.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    private final static int CORE_POOL_SIZE = 15;
    private final static int MAX_POOL_SIZE = 20;
    private final static int QUEUE_CAPACITY = 30;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(
            JobRepository jobRepository, PlatformTransactionManager transactionManager
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix("job-thread-");
        return taskExecutor;
    }

    @Bean
    public Job retrieveDataJob() {
        return new JobBuilder("retrieveDataJob", jobRepository)
                .start(mainFlow())
                .build()
                .build();
    }

    @Bean
    public Flow mainFlow() {
        return new FlowBuilder<SimpleFlow>("mainFlow")
                .split(taskExecutor())
                .add()
                .build();
    }

}
