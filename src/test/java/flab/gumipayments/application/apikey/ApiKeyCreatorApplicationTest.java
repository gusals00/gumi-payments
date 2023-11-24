package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ApiKeyCreatorApplicationTest {

    @InjectMocks
    private ApiKeyCreatorApplication sut;
    @Mock
    private ApiKeyPairFactory apiKeyPairFactory;
    @Mock
    private ApiKeyFactory apiKeyFactory;
    private ApiKeyPair.ApiKeyPairBuilder apiKeyPairBuilder;
    private ApiKey.ApiKeyBuilder apiKeyBuilder;

    @BeforeEach
    void setup() {
        apiKeyPairBuilder = ApiKeyPair.builder();
        apiKeyBuilder = ApiKey.builder();
    }

    @Test
    @DisplayName("성공: API 키 생성을 성공한다.")
    void create() {
        ApiKeyCreateCommand command = ApiKeyCreateCommand.builder().build();
        when(apiKeyPairFactory.create()).thenReturn(apiKeyPairBuilder.build());
        when(apiKeyFactory.create(any(), any())).thenReturn(apiKeyBuilder.build());

        sut.create(command);

        verify(apiKeyFactory).create(any(), any());
    }
}