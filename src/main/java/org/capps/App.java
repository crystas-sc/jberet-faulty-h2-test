package org.capps;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.JobExecution;
import javax.inject.Inject;
import io.quarkus.runtime.QuarkusMain;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class App {
    @Inject
    JobOperator jobOperator;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {
        JobExecution execution = jobOperator.startJob("myJob");
        System.out.println("Job execution ID: " + execution.getExecutionId());
    }
}
