package flab.gumipayments.domain;

import java.util.UUID;

public class KeyFactory {

    public static String generateSignupKey() {
        return UUID.randomUUID().toString();
    }
}
