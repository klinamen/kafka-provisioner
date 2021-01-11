package io.klinamen.kafkaprovisioner.service;

import io.klinamen.kafkaprovisioner.model.KafkaState;

public interface ProvisionService {
    void provision(KafkaState state);
}
