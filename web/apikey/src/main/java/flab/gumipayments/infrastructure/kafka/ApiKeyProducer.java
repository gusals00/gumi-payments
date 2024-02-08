package flab.gumipayments.infrastructure.kafka;

import flab.gumipayments.domain.ApiKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ApiKeyProducer {

    private final KafkaTemplate<String, ApiKeyMessageDTO> kafkaTemplate;
    private final String topic = "api-key";

    public void sendApiKeyMessage(ApiKeyMessageDTO message, MessageType type) {
        log.info("producer/ topic = {}, type = {}", topic, type);
        kafkaTemplate.send(topic, type.name(), message);
    }

}
