package flab.gumipayments.application;

import flab.gumipayments.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static flab.gumipayments.domain.ApiKey.*;
import static flab.gumipayments.domain.ApiKeyPair.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ApiKeyCreatorRequesterApplicationTest {

    @InjectMocks
    private ApiKeyCreatorRequesterApplication sut;
    @Mock
    private ApiKeyPairFactory apiKeyPairFactory;
    @Mock
    private ApiKeyFactory apiKeyFactory;
    private ApiKeyPairBuilder apiKeyPairBuilder;
    private ApiKeyBuilder apiKeyBuilder;

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