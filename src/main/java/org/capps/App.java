package org.capps;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;
import org.jberet.runtime.JobExecutionImpl;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@ApplicationScoped
@QuarkusMain
public class App implements QuarkusApplication {

    QuarkusJobOperator jobOperator;

    @Inject
    public App(QuarkusJobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    public static void main(String[] args) {
        Quarkus.run(App.class, args);
        // main.run();
    }

    @Override
    public int run(String... args) {

        runJob();
        return 0;
    }

    public void runJob() {
        Job job = new JobBuilder("myJob")
                .step(new StepBuilder("myStep")
                        .reader("myItemReader")
                        .processor("myItemProcessor")
                        .writer("myItemWriter")
                        .itemCount(10)

                        .build())
                .build();

        long executionId = jobOperator.start(job, new Properties());
        JobExecutionImpl jobExecution = (JobExecutionImpl) jobOperator.getJobExecution(executionId);
        try {
            jobExecution.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
