package flab.gumipayments.domain.apikey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApiKeyTest {

    private ApiKey.ApiKeyBuilder apiKeyBuilder;
    private ApiKey sut;

    @BeforeEach
    void setup() {
        apiKeyBuilder = ApiKey.builder();
    }

    @Test
    @DisplayName("예외: 기존 만료 기간과 연장할 만료 시간이 동일한 경우, 키의 만료 기간 연장을 실패한다.")
    void extendApiKeyExpireFail01() {
        LocalDateTime expireDate = now();
        sut = apiKeyBuilder.expireDate(expireDate).build();

        assertThatThrownBy(() -> sut.extendExpireDate(expireDate)).isInstanceOf(ApiKeyExpireException.class);
    }

    @Test
    @DisplayName("예외: 기존 만료 기간이 연장할 만료 시간보다 이전인 경우, 키 만료 기간 연장을 실패한다.")
    void extendApiKeyExpireFail02() {
        LocalDateTime expireDate = now();
        LocalDateTime extendExpireDate = expireDate.minusNanos(1);
        sut = apiKeyBuilder.expireDate(expireDate).build();

        assertThatThrownBy(() -> sut.extendExpireDate(extendExpireDate)).isInstanceOf(ApiKeyExpireException.class);
    }
    @Test
    @DisplayName("성공: 키의 만료 기간을 연장 시, 키 만료 기간 연장이 성공한다.")
    void extendApiKeyExpire() {
        LocalDateTime expireDate = now();
        LocalDateTime extendExpireDate = expireDate.plusNanos(1);
        sut = apiKeyBuilder.expireDate(expireDate).build();

        sut.extendExpireDate(extendExpireDate);

        assertThat(sut.getExpireDate().isEqual(extendExpireDate)).isTrue();
    }

}