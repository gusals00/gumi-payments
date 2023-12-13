package flab.gumipayments.domain.apikey;

import flab.gumipayments.infrastructure.encrypt.KeyEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyFactory {
    private final KeyEncrypt keyEncrypt;

    public ApiKey create(ApiKeyCreateCommand createCommand, String secretKey) {
        return ApiKey.builder()
                .secretKey(keyEncrypt.encrypt(secretKey))
                .accountId(createCommand.getAccountId())
                .type(createCommand.getKeyType())
                .expireDate(createCommand.getExpireDate())
                .build();
    }
}
