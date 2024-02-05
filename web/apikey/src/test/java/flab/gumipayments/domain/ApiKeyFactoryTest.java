package flab.gumipayments.domain;

import flab.gumipayments.infrastructure.KeyEncrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.ApiKeyCreateCommand.*;
import static flab.gumipayments.domain.ApiKeyPair.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyFactoryTest {

    @InjectMocks
    private ApiKeyFactory sut;
    @Mock
    private KeyEncrypt keyEncrypt;
    private ApiKeyCreateCommandBuilder apiKeyCreateCommandBuilder;
    private ApiKeyPairBuilder apiKeyPairBuilder;
    @BeforeEach
    void setup() {
        apiKeyCreateCommandBuilder = ApiKeyCreateCommand.builder();
        apiKeyPairBuilder = ApiKeyPair.builder();
    }

    @Test
    @DisplayName("성공: API 키 생성을 성공한다.")
    void create() {
        ApiKeyCreateCommand createCommand = apiKeyCreateCommandBuilder
                .keyType(ApiKeyType.TEST)
                .accountId(1L)
                .expireDate(LocalDateTime.now())
                .build();
        ApiKeyPair apiKeyPair = apiKeyPairBuilder
                .clientKey("123")
                .secretKey("345")
                .build();

        String encryptedSecretKey = "encryptedSecret";
        String encryptedClientKey = "encryptedClient";


        when(keyEncrypt.encrypt(any()))
                .thenReturn(encryptedSecretKey)
                .thenReturn(encryptedClientKey);

        ApiKey apiKey = sut.create(createCommand, apiKeyPair);

        assertThat(apiKey.getSecretKey()).isEqualTo(encryptedSecretKey);
        assertThat(apiKey.getClientKey()).isEqualTo(encryptedClientKey);
        assertThat(apiKey.getAccountId()).isEqualTo(createCommand.getAccountId());
        assertThat(apiKey.getExpireDate()).isEqualTo(createCommand.getExpireDate());
        assertThat(apiKey.getType()).isEqualTo(createCommand.getKeyType());
    }
}