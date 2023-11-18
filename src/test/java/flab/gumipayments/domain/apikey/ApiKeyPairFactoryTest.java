package flab.gumipayments.domain.apikey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.*;
import static org.assertj.core.api.Assertions.*;


class ApiKeyPairFactoryTest {

    @Test
    @DisplayName("성공: api key pair 생성을 성공한다.")
    void create() {
        ApiKeyPairFactory apiKeyPairFactory = new ApiKeyPairFactory();

        ApiKeyPair apiKeyPair = apiKeyPairFactory.generateApiKeyPair();

        assertThat(apiKeyPair.getClientKey().startsWith(CLIENT_KEY_PREFIX)).isTrue();
        assertThat(apiKeyPair.getSecretKey().startsWith(SECRET_KEY_PREFIX)).isTrue();
    }
}