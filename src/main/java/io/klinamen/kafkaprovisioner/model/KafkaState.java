package io.klinamen.kafkaprovisioner.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class KafkaState {
    @NotNull
    @Valid
    private List<Topic> topics = new ArrayList<>();
}

