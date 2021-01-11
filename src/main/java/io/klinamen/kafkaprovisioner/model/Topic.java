package io.klinamen.kafkaprovisioner.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Topic {
    @NotEmpty
    private String name;
    @Positive
    private int numPartitions;
    @Positive
    private int replicationFactor;
    private Map<String, String> configs;
}
