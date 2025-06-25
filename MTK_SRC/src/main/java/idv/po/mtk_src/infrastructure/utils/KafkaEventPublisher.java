package idv.po.mtk_src.infrastructure.utils;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public <T> void publish(String topic, String key, T event) {
        kafkaTemplate.send(topic, key, event);
    }

    public <T> void publishAll(List<ProducerRecord<String, Object>> kafkaRecords) {
        kafkaRecords.forEach(kafkaTemplate::send);
    }

}
