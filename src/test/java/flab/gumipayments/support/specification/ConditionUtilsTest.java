package flab.gumipayments.support.specification;

import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import flab.gumipayments.domain.apikey.ReIssueFactor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.support.specification.ConditionUtils.*;
import static flab.gumipayments.support.specification.ConditionUtils.or;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionUtilsTest {
    private static final Condition<Object> alwaysTrue = command -> true;
    private static final Condition<Object> alwaysFalse = command -> false;
    private static final Object factor = new Object();

    private Condition<Object> sut;

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not()이라면 조건을 만족한다.")
    void notTrue03() {
        sut = not(alwaysFalse);
        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 모두가 참이라면 and(...)을 만족한다.")
    void andArgs01() {
        sut = and(alwaysTrue, alwaysTrue, alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 중 하나라도 참이 아니라면 and(...)을 만족하지 않는다.")
    void andArgs02() {
        sut = and(alwaysTrue, alwaysFalse, alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건 중 하나라도 참이라면 or(...)을 만족한다.")
    void orArgs01() {
        sut = or(alwaysFalse, alwaysFalse, alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 조건 모두가 거짓이라면 or(...)을 만족하지 않는다.")
    void orArgs02() {
        sut = or(alwaysFalse, alwaysFalse, alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }
}