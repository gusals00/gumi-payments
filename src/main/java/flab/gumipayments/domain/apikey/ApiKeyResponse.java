package flab.gumipayments.domain.apikey;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiKeyResponse {

    private ApiKeyPair apiKeyPair;
    private ApiKey apiKey;
}
