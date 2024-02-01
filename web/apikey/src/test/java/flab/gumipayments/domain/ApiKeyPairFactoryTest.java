package flab.gumipayments.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ApiKeyPairPrefix.CLIENT_KEY_PREFIX;
import static flab.gumipayments.domain.ApiKeyPairPrefix.SECRET_KEY_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyPairFactoryTest {

    @Test
    @DisplayName("성공: API 키 쌍 생성을 성공한다.")
    void create() {
        ApiKeyPairFactory sut = new ApiKeyPairFactory();

        ApiKeyPair apiKeyPair = sut.create();

        assertThat(apiKeyPair.getSecretKey().startsWith(SECRET_KEY_PREFIX)).isTrue();
        assertThat(apiKeyPair.getClientKey().startsWith(CLIENT_KEY_PREFIX)).isTrue();
    }
}