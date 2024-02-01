package flab.gumipayments.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiKeyPair {

    private String secretKey;
    private String clientKey;

    @Builder
    public ApiKeyPair(String secretKey, String clientKey) {
        this.secretKey = secretKey;
        this.clientKey = clientKey;
    }
}
