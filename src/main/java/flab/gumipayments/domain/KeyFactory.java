package flab.gumipayments.domain;

import java.util.UUID;

public class KeyFactory {
    public static String createSignupKey() {
        return UUID.randomUUID().toString();
    }
}
