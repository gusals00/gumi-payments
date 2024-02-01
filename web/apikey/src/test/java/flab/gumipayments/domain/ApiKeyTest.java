package flab.gumipayments.domain;

import flab.gumipayments.application.ApiKeyRenewCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static flab.gumipayments.application.ApiKeyRenewCommand.*;
import static flab.gumipayments.domain.ApiKey.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApiKeyTest {

    private ApiKeyBuilder apiKeyBuilder;
    private ApiKey sut;
    private ApiKeyRenewCommandBuilder renewCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyBuilder = ApiKey.builder();
        renewCommandBuilder = ApiKeyRenewCommand.builder();
    }

    @Test
    @DisplayName("예외: 연장하려는 날짜가 만료날짜와 같은 경우 API 키 기간 연장이 실패한다.")
    void extendKeyFail01() {
        LocalDateTime expireDate = LocalDateTime.of(2023,1,1,1,0);
        sut = apiKeyBuilder.expireDate(expireDate).build();

        assertThatThrownBy(()->sut.extendExpireDate(renewCommandBuilder.extendDate(expireDate).build()))
                .isInstanceOf(ApiKeyExtendException.class);
    }

    @Test
    @DisplayName("예외: 연장하려는 날짜가 만료날짜 이전인 경우 API 키 기간 연장이 실패한다.")
    void extendKeyFail02() {
        LocalDateTime expireDate = LocalDateTime.of(2023,1,1,1,0);
        LocalDateTime extendDate = expireDate.minusNanos(1);

        sut = apiKeyBuilder.expireDate(expireDate).build();

        assertThatThrownBy(()->sut.extendExpireDate(renewCommandBuilder.extendDate(extendDate).build()))
                .isInstanceOf(ApiKeyExtendException.class);
    }

    @Test
    @DisplayName("성공: API 키 기간 연장을 성공한다.")
    void extendKey() {
        LocalDateTime expireDate = LocalDateTime.of(2023,1,1,1,0);
        LocalDateTime extendDate = expireDate.plusNanos(1);
        sut = apiKeyBuilder.expireDate(expireDate).build();

        sut.extendExpireDate(renewCommandBuilder.extendDate(extendDate).build());

        assertThat(sut.getExpireDate().isEqual(extendDate)).isTrue();
    }
}