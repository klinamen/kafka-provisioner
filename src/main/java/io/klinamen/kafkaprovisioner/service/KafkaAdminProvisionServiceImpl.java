package io.klinamen.kafkaprovisioner.service;

import io.klinamen.kafkaprovisioner.model.KafkaState;
import io.klinamen.kafkaprovisioner.model.Topic;
import io.klinamen.kafkaprovisioner.service.ProvisionService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaAdminProvisionServiceImpl implements ProvisionService {
    private final KafkaProperties kafkaProperties;

    public KafkaAdminProvisionServiceImpl(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Override
    public void provision(KafkaState state) {
        try (AdminClient client = KafkaAdminClient.create(kafkaProperties.buildAdminProperties())) {
            syncTopics(state, client);
        }
    }

    private void syncTopics(KafkaState state, AdminClient client){
        List<NewTopic> newTopics = new ArrayList<>();

        for (Topic topic : state.getTopics()) {
            TopicBuilder builder = TopicBuilder.name(topic.getName())
                    .partitions(topic.getNumPartitions())
                    .replicas(topic.getReplicationFactor());

            if (topic.getConfigs() != null) {
                builder.configs(topic.getConfigs());
            }

            newTopics.add(builder.build());
        }

        if(!newTopics.isEmpty()){
            client.createTopics(newTopics);
        }
    }
}
