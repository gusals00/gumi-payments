package flab.gumipayments.domain.apikey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.support.specification.ConditionUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class ApikeyIssueConditionTest {

    private ApiKeyIssueCondition alwaysTrue = command -> true;
    private ApiKeyIssueCondition alwaysFalse = command -> false;
    private IssueFactor issueFactor = IssueFactor.builder().build();

    private ApiKeyIssueCondition sut;

    @Test
    @DisplayName("조건: and()로 연결한 조건이 모두 참이면 API 키 발급 조건을 만족한다.")
    void andTrue01() {
        sut = alwaysTrue.and(alwaysTrue);

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 중 하나라도 참이 아니면 API 키 발급 조건을 만족하지 않는다.")
    void andTrue02() {
        sut = alwaysTrue.and(alwaysFalse);

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 중 하나라도 참이라면 API 키 발급 조건을 만족한다.")
    void orTrue01() {
        sut = alwaysTrue.or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건이 모두 거짓이라면 API 키 발급 조건을 만족하지 않는다.")
    void orTrue02() {
        sut = alwaysFalse.or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 참일 때 not() 이라면 API 키 발급 조건을 만족하지 않는다.")
    void notTrue01() {
        sut = alwaysTrue.not();

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not() 이라면 API 키 발급 조건을 만족한다.")
    void notTrue02() {
        sut = alwaysFalse.not();

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }
}