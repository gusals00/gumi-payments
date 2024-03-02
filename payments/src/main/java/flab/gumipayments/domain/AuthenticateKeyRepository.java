package flab.gumipayments.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticateKeyRepository {

    private Map<String,String> keyMap = new HashMap<>();
    public void save(String randomKey, String paymentKey) {
        keyMap.put(randomKey,paymentKey);
    }

    public boolean isValid(String randomKey, String paymentKey) {
        return keyMap.containsKey(randomKey) && keyMap.get(randomKey).equals(paymentKey);
    }
}
