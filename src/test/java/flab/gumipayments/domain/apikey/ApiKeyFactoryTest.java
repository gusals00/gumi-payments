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
        apiKeyCreateCommandBuilder = ApiKeyCreateCommand.builder();
    }

    @Test
    @DisplayName("성공: 암호화된 API 키 생성을 성공한다.")
    void create() {
        ApiKeyCreateCommand createCommand = apiKeyCreateCommandBuilder.build();
        String secretKey = "123";
        String encryptedKey = "encrypted";
        when(encoder.encode(any())).thenReturn(encryptedKey);

        ApiKey apiKey = sut.create(createCommand, secretKey);

        assertThat(apiKey.getSecretKey()).isEqualTo(encryptedKey);
    }
}