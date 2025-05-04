package idv.po.mtk_src.infrastructure.utils;

import idv.po.mtk_src.movie.domain.event.ShowtimeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    public <T> void publish(String topic, UUID key, T event) {
        kafkaTemplate.send(topic, key, event);
    }

    public <T> void publishAll(List<ProducerRecord<UUID, Object>> kafkaRecords) {
        kafkaRecords.forEach(record->kafkaTemplate.send (record));
    }

}
