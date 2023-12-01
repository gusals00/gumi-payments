package flab.gumipayments.domain.apikey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ApiKeyReIssueCondition.*;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyReIssueConditionTest {
    private ApiKeyReIssueCondition trueCondition = command -> true;
    private ApiKeyReIssueCondition falseCondition = command -> false;
    private ReIssueCommand reIssueComand = ReIssueCommand.builder().build();

    private ApiKeyReIssueCondition sut;

    @Test
    @DisplayName("조건: and()로 연결한 조건이 모두 참이면 API 키 재발급 조건을 만족한다.")
    void andTrue01() {
        sut = trueCondition.and(trueCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 중 하나라도 참이 아니면 API 키 재발급 조건을 만족하지 않는다.")
    void andTrue02() {
        sut = trueCondition.and(falseCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 중 하나라도 참이라면 API 키 재발급 조건을 만족한다.")
    void orTrue01() {
        sut = trueCondition.or(falseCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건이 모두 거짓이라면 API 키 재발급 조건을 만족하지 않는다.")
    void orTrue02() {
        sut = falseCondition.or(falseCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 참일 때 not() 이라면 API 키 재발급 조건을 만족하지 않는다.")
    void notTrue01() {
        sut = trueCondition.not();

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not() 이라면 API 키 재발급 조건을 만족한다.")
    void notTrue02() {
        sut = falseCondition.not();

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not() 이라면 API 키 재발급 조건을 만족한다.")
    void notTrue03() {
        sut = not(falseCondition);
        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 모두가 참이라면 and(...)을 만족한다.")
    void andArgs01() {
        sut = and(trueCondition, trueCondition, trueCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 중 하나라도 참이 아니라면 and(...)을 만족하지 않는다.")
    void andArgs02() {
        sut = and(trueCondition, falseCondition, trueCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건 중 하나라도 참이라면 or(...)을 만족한다.")
    void orArgs01() {
        sut = or(falseCondition, falseCondition, trueCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 모두가 거짓이라면 or(...)을 만족하지 않는다.")
    void orArgs02() {
        sut = or(falseCondition, falseCondition, falseCondition);

        boolean result = sut.isSatisfiedBy(reIssueComand);

        assertThat(result).isFalse();
    }
}