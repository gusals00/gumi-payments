package flab.gumipayments.domain.apikey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.CLIENT_KEY_PREFIX;
import static flab.gumipayments.domain.apikey.ApiKeyPairPrefix.SECRET_KEY_PREFIX;
import static org.assertj.core.api.Assertions.*;

class ApiKeyPairFactoryTest {

    @Test
    @DisplayName("성공: api 키 쌍 생성을 성공한다.")
    void create() {
        ApiKeyPairFactory sut = new ApiKeyPairFactory();

        ApiKeyPair apiKeyPair = sut.create();

        assertThat(apiKeyPair.getSecretKey().startsWith(SECRET_KEY_PREFIX)).isTrue();
        assertThat(apiKeyPair.getClientKey().startsWith(CLIENT_KEY_PREFIX)).isTrue();
    }
}