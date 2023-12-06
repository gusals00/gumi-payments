package flab.gumipayments.support.proxyspecification;

import flab.gumipayments.domain.apikey.IssueFactor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.support.proxyspecification.Condition2.*;
import static org.assertj.core.api.Assertions.assertThat;

class Condition2Test {
    private IssueCondition sut;
    private static IssueFactor factor;
    private static IssueCondition alwaysTrue;
    private static IssueCondition alwaysFalse;

    @BeforeAll
    static void setAll() {
        alwaysTrue = (command) -> true;
        alwaysFalse = (command) -> false;
        factor = IssueFactor.builder().build();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 2개가 모두 참이면 조건을 만족한다.")
    void andTrue01() {
        sut = (IssueCondition) alwaysTrue.and(alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 2개 중 하나라도 참이 아니면 조건을 만족하지 않는다.")
    void andTrue02() {
        sut = (IssueCondition) alwaysTrue.and(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 2개 중 하나라도 참이라면 조건을 만족한다.")
    void orTrue01() {
        sut = (IssueCondition) alwaysTrue.or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 2개가 모두 거짓이라면 조건을 만족하지 않는다.")
    void orTrue02() {
        sut = (IssueCondition) alwaysFalse.or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 참일 때 not() 이라면 조건을 만족하지 않는다.")
    void notTrue01() {
        sut = (IssueCondition) alwaysTrue.not();

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not() 이라면 조건을 만족한다.")
    void notTrue02() {
        sut = (IssueCondition) alwaysFalse.not();

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 3개 모두 참이면 조건을 만족한다.")
    void andTrueMultiple01() {
        sut = (IssueCondition) alwaysTrue.and(alwaysTrue).and(alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 3개 중 하나라도 참이 아니면 조건을 만족하지 않는다.")
    void andTrueMultiple02() {
        sut = (IssueCondition) alwaysFalse.and(alwaysTrue).and(alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 3개 중 하나라도 참이라면 조건을 만족한다.")
    void orTrueMultiple01() {
        sut = (IssueCondition) alwaysTrue.or(alwaysFalse).or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 3개가 모두 거짓이라면 조건을 만족하지 않는다.")
    void orTrueMultiple02() {
        sut = (IssueCondition) alwaysFalse.or(alwaysFalse).or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not()이라면 조건을 만족한다.")
    void notTrueStatic() {
        sut = (IssueCondition) not(alwaysFalse);
        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 모두가 참이라면 and(...)을 만족한다.")
    void andArgs01() {
        sut = (IssueCondition) and(alwaysTrue, alwaysTrue, alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 중 하나라도 참이 아니라면 and(...)을 만족하지 않는다.")
    void andArgs02() {
        sut = (IssueCondition) and(alwaysTrue, alwaysFalse, alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건 중 하나라도 참이라면 or(...)을 만족한다.")
    void orArgs01() {
        sut = (IssueCondition)or(alwaysFalse, alwaysFalse, alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 모두가 거짓이라면 or(...)을 만족하지 않는다.")
    void orArgs02() {
        sut = (IssueCondition)or(alwaysFalse, alwaysFalse, alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }
}