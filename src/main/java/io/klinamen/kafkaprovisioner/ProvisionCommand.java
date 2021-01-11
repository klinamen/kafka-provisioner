package io.klinamen.kafkaprovisioner;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.klinamen.kafkaprovisioner.model.KafkaState;
import io.klinamen.kafkaprovisioner.service.ProvisionService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.io.File;
import java.util.Set;
import java.util.concurrent.Callable;

@Component
@Command(name = "provision")
public class ProvisionCommand implements Callable<Integer> {
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final ProvisionService provisionService;

    @Parameters(index = "0", description = "JSON file containing the desired state")
    File state;

    public ProvisionCommand(ProvisionService provisionService, ObjectMapper objectMapper, Validator validator) {
        this.provisionService = provisionService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public Integer call() throws Exception {
        KafkaState kafkaState = objectMapper.readValue(state, KafkaState.class);

        Set<ConstraintViolation<KafkaState>> violations = validator.validate(kafkaState);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }

        provisionService.provision(kafkaState);
        return 0;
    }
}
