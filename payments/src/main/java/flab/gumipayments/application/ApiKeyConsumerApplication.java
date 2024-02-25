package flab.gumipayments.application;

import flab.gumipayments.domain.ApiKey;
import flab.gumipayments.domain.ApiKeyRepository;

import flab.gumipayments.infrastructure.kafka.ApiKeyMessageDTO;
import flab.gumipayments.infrastructure.kafka.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApiKeyConsumerApplication {

    private final ApiKeyRepository apiKeyRepository;

    @KafkaListener(topics = "api-key", groupId = ConsumerConfig.GROUP_ID_CONFIG)
    public void consume(ConsumerRecord<String, ApiKeyMessageDTO> consumerRecord) throws IOException {
        ApiKey apiKey = convertToApiKey(consumerRecord.value());

        if(consumerRecord.key().equals(MessageType.INSERT.name())){ // api key 저장
            apiKeyRepository.save(apiKey);
        }
        else{ // api key 삭제
            apiKeyRepository.deleteById(apiKey.getId());
        }

        log.info("Consumed message, type = {}, apiKeyId = {}, encrypted secretKey = {}", consumerRecord.key(), consumerRecord.value().getId(), consumerRecord.value().getSecretKey());
    }

    private ApiKey convertToApiKey(ApiKeyMessageDTO message) {
        return ApiKey.builder()
                .id(message.getId())
                .secretKey(message.getSecretKey())
                .clientKey(message.getClientKey())
                .accountId(message.getAccountId())
                .type(message.getType())
                .expireDate(message.getExpireDate())
                .count(message.getCount())
                .build();
    }
}
