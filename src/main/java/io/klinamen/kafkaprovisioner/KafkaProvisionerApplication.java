package io.klinamen.kafkaprovisioner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@SpringBootApplication
public class KafkaProvisionerApplication implements CommandLineRunner, ExitCodeGenerator {
    private final IFactory factory;
    private final ProvisionCommand command;

    private int exitCode;

    KafkaProvisionerApplication(IFactory factory, ProvisionCommand command) {
        this.factory = factory;
        this.command = command;
    }

    @Override
    public void run(String... args) {
        // let picocli parse command line args and run the business logic
        exitCode = new CommandLine(command, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        // let Spring instantiate and inject dependencies
        System.exit(SpringApplication.exit(SpringApplication.run(KafkaProvisionerApplication.class, args)));
    }
}