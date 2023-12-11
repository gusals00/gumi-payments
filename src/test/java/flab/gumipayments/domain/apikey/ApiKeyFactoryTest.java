package flab.gumipayments.domain.apikey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.apikey.ApiKeyCreateCommand.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyFactoryTest {

    @InjectMocks
    private ApiKeyFactory sut;
    @Mock
    private PasswordEncoder encoder;
    private ApiKeyCreateCommandBuilder apiKeyCreateCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyCreateCommandBuilder = builder();
    }

    @Test
    @DisplayName("성공: API 키 생성을 성공한다.")
    void create() {
        ApiKeyCreateCommand createCommand = apiKeyCreateCommandBuilder
                .keyType(ApiKeyType.TEST)
                .accountId(1L)
                .expireDate(LocalDateTime.now())
                .build();
        String secretKey = "123";
        String encryptedKey = "encrypted";
        when(encoder.encode(any())).thenReturn(encryptedKey);

        ApiKey apiKey = sut.create(createCommand, secretKey);

        assertThat(apiKey.getSecretKey()).isEqualTo(encryptedKey);
        assertThat(apiKey.getAccountId()).isEqualTo(createCommand.getAccountId());
        assertThat(apiKey.getExpireDate()).isEqualTo(createCommand.getExpireDate());
        assertThat(apiKey.getType()).isEqualTo(createCommand.getKeyType());
    }
}