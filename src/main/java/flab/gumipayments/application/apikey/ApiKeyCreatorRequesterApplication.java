package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.*;
import flab.gumipayments.domain.apikey.ApiKeyCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ApiKeyCreatorRequesterApplication {

    private final ApiKeyPairFactory apiKeyPairFactory;
    private final ApiKeyFactory apiKeyFactory;

    // api 키 생성
    public ApiKeyResponse create(ApiKeyCreateCommand command) {
        // 시크릿, 클라이언트 키 생성
        ApiKeyPair apiKeyPair = apiKeyPairFactory.create();

        // api 키 생성
        ApiKey apiKey = apiKeyFactory.create(command, apiKeyPair.getSecretKey());

        return new ApiKeyResponse(apiKeyPair, apiKey);
    }
}
