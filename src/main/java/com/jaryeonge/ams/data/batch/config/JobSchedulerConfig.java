package com.jaryeonge.ams.data.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@Configuration
@Slf4j
public class JobSchedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job retrieveDataJob;

    public JobSchedulerConfig(JobLauncher jobLauncher, Job retrieveDataJob) {
        this.jobLauncher = jobLauncher;
        this.retrieveDataJob = retrieveDataJob;
    }

    @Scheduled(cron = "0 * * * * *")
    public void jobScheduled() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder()
                .addString("id", UUID.randomUUID().toString())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(retrieveDataJob, parameters);

        log.info("Job Execution: " + jobExecution.getStatus());
        log.info("Job getJobId: " + jobExecution.getJobId());
        log.info("Job getExitStatus: " + jobExecution.getExitStatus());
        log.info("Job getJobInstance: " + jobExecution.getJobInstance());
        log.info("Job getStepExecutions: " + jobExecution.getStepExecutions());
        log.info("Job getLastUpdated: " + jobExecution.getLastUpdated());
        log.info("Job getFailureExceptions: " + jobExecution.getFailureExceptions());

    }
}
