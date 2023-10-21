package flab.gumipayments.application;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KeyGeneratorApplication {

    public String generateSignupKey() {
        return UUID.randomUUID().toString();
    }
}
