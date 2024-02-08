package flab.gumipayments.specification;

import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.support.specification.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionTest {

    private Condition<Object> sut;
    private static Object factor;
    private static Condition<Object> alwaysTrue;
    private static Condition<Object> alwaysFalse;

    @BeforeAll
    static void setAll() {
        alwaysTrue = (command) -> true;
        alwaysFalse = (command) -> false;
        factor = new Object();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건이 모두 참이면 조건을 만족한다.")
    void andTrue01() {
        sut = alwaysTrue.and(alwaysTrue);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: and()로 연결한 조건 중 하나라도 참이 아니면 조건을 만족하지 않는다.")
    void andTrue02() {
        sut = alwaysTrue.and(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건 중 하나라도 참이라면 조건을 만족한다.")
    void orTrue01() {
        sut = alwaysTrue.or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: or()로 연결한 조건이 모두 거짓이라면 조건을 만족하지 않는다.")
    void orTrue02() {
        sut = alwaysFalse.or(alwaysFalse);

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 참일 때 not() 이라면 조건을 만족하지 않는다.")
    void notTrue01() {
        sut = alwaysTrue.not();

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: 조건이 거짓일 때 not() 이라면 조건을 만족한다.")
    void notTrue02() {
        sut = alwaysFalse.not();

        boolean result = sut.isSatisfiedBy(factor);

        assertThat(result).isTrue();
    }

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