package flab.gumipayments.domain.apikey;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyFactory {
    private final PasswordEncoder encoder;

    public ApiKey create(ApiKeyCreateCommand createCommand, String secretKey) {
        return ApiKey.builder()
                .secretKey(encoder.encode(secretKey))
                .accountId(createCommand.getAccountId())
                .type(createCommand.getKeyType())
                .expireDate(createCommand.getExpireDate())
                .build();
    }
}
