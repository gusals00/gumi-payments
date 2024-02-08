package flab.gumipayments.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ApiKeyConsumer {
    @KafkaListener(topics = "api-key", groupId = ConsumerConfig.GROUP_ID_CONFIG)
    public void consume(ConsumerRecord<String, ApiKeyMessageDTO> consumerRecord) throws IOException {

        log.info("Consumed message : {} key : {}", consumerRecord.value().getName(), consumerRecord.key());
    }
}
