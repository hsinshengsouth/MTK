package idv.po.mtk_src.infrastructure.config;


import idv.po.mtk_src.movie.domain.event.MovieCreatedEvent;
import idv.po.mtk_src.movie.domain.event.ShowtimeCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // --- Topic Config ---
    @Bean
    public NewTopic movieCreatedTopic(@Value("${kafka.topic.movie-created}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic showtimeCreatedTopic(@Value("${kafka.topic.showtime-created}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }



   @Bean
    public<T>  ProducerFactory<String, T> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

       config.put(ProducerConfig.ACKS_CONFIG, "all");
       config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
       config.put(ProducerConfig.RETRIES_CONFIG, 3);

        return new DefaultKafkaProducerFactory<>(config);
    }


    public <T> ConcurrentKafkaListenerContainerFactory<String, T> createListenerFactory(Class<T> valueType) {
        // Configure deserializer
        JsonDeserializer<T> valueDeserializer = new JsonDeserializer<>(valueType);
        valueDeserializer.addTrustedPackages("*");
        valueDeserializer.setUseTypeMapperForKey(false);
        valueDeserializer.setRemoveTypeHeaders(true);

        ConsumerFactory<String, T> consumerFactory = new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
                ),
                new StringDeserializer(),
                valueDeserializer
        );

        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MovieCreatedEvent> movieKafkaListenerContainerFactory(
            KafkaConfig config) {
        return config.createListenerFactory(MovieCreatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ShowtimeCreatedEvent> showtimeKafkaListenerContainerFactory(
            KafkaConfig config) {
        return config.createListenerFactory(ShowtimeCreatedEvent.class);
    }


}
