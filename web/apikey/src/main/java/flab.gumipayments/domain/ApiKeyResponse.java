package flab.gumipayments.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ApiKeyResponse {

    private ApiKeyPair apiKeyPair;
    private ApiKey apiKey;
}
