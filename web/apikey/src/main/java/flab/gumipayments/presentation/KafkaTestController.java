package flab.gumipayments.presentation;

import flab.gumipayments.infrastructure.kafka.ApiKeyMessageDTO;
import flab.gumipayments.infrastructure.kafka.ApiKeyProducer;
import flab.gumipayments.infrastructure.kafka.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/kafka")
public class KafkaTestController {
    private final ApiKeyProducer producer;

    @PostMapping(value = "/message")
    public String sendMessage(@RequestParam("message") String message) {
        this.producer.sendApiKeyMessage(ApiKeyMessageDTO.builder().name(message).build(), MessageType.INSERT);
        return "success";
    }
}
