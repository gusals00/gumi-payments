package flab.gumipayments.domain.apikey;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.apikey.ApiKey.*;
import static org.assertj.core.api.Assertions.*;

class ApiKeyTest {

    private ApiKeyBuilder apiKeyBuilder;
    private ApiKey sut;

    @BeforeEach
    void setup() {
        apiKeyBuilder = ApiKey.builder();
    }

    @Test
    @DisplayName("예외: 연장하려는 날짜가 만료날짜와 같은 경우 API 키 기간 연장이 실패한다.")
    void extendKeyFail01() {
        LocalDateTime expireDate = LocalDateTime.of(2023,1,1,1,0);
        sut = apiKeyBuilder.expireDate(expireDate).build();

        assertThatThrownBy(()->sut.extendExpireDate(expireDate))
                .isInstanceOf(ApiKeyExpireException.class);
    }

    @Test
    @DisplayName("예외: 연장하려는 날짜가 만료날짜 이전인 경우 API 키 기간 연장이 실패한다.")
    void extendKeyFail02() {
        LocalDateTime expireDate = LocalDateTime.of(2023,1,1,1,0);
        LocalDateTime extendDate = expireDate.minusNanos(1);

        sut = apiKeyBuilder.expireDate(expireDate).build();

        assertThatThrownBy(()->sut.extendExpireDate(extendDate))
                .isInstanceOf(ApiKeyExpireException.class);
    }

    @Test
    @DisplayName("성공: API 키 기간 연장을 성공한다.")
    void extendKey() {
        LocalDateTime expireDate = LocalDateTime.of(2023,1,1,1,0);
        LocalDateTime extendDate = expireDate.plusNanos(1);
        sut = apiKeyBuilder.expireDate(expireDate).build();

        sut.extendExpireDate(extendDate);

        assertThat(sut.getExpireDate().isEqual(extendDate)).isTrue();
    }
}