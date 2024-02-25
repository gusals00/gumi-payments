package flab.gumipayments.domain;

import flab.gumipayments.infrastructure.KeyEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyFactory {
    private final KeyEncrypt keyEncrypt;

    public ApiKey create(ApiKeyCreateCommand createCommand, ApiKeyPair apiKeyPair) {
        return ApiKey.builder()
                .secretKey(keyEncrypt.encrypt(apiKeyPair.getSecretKey()))
                .clientKey(keyEncrypt.encrypt(apiKeyPair.getClientKey()))
                .accountId(createCommand.getAccountId())
                .type(createCommand.getKeyType())
                .expireDate(createCommand.getExpireDate())
                .build();
    }
}
