package flab.gumipayments.domain.apikey;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiKeyFactoryTest {

    @Mock
    PasswordEncoder encoder;
    @InjectMocks
    ApiKeyFactory sut;
    ApiKeyCreateCommand.ApiKeyCreateCommandBuilder apiKeyCreateCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyCreateCommandBuilder = ApiKeyCreateCommand.builder();
    }

    @Test
    @DisplayName("성공: 암호화된 api 키 생성을 성공한다.")
    void createApiKey() {
        String encryptedSecretKey ="!@#$sd";
        when(encoder.encode(anyString())).thenReturn(encryptedSecretKey);
        ApiKeyCreateCommand createCommand = apiKeyCreateCommandBuilder
                .secretKey("1234")
                .build();

        ApiKey apiKey = sut.create(createCommand);

        assertThat(apiKey.getSecretKey()).isEqualTo(encryptedSecretKey);
    }
}