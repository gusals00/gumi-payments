package flab.gumipayments.support.proxyspecification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionFactoryTest {

    private static ConditionSubInterface alwaysTrue = factor -> true;
    private static ConditionSubInterface alwaysFalse = factor -> false;
    private Condition2<Object> sut;

    @Test
    @DisplayName("구현: Condition<T> 구현체로 andCondition을 구현한다.")
    void andConditionImpl() {
        sut = ConditionFactory.createAndCondition(alwaysTrue, alwaysFalse);

        assertThat(sut instanceof ConditionSubInterface).isTrue();
    }

    @Test
    @DisplayName("구현: Condition<T> 구현체로 orCondition을 구현한다.")
    void orConditionImpl() {
        sut = ConditionFactory.createOrCondition(alwaysTrue, alwaysFalse);

        assertThat(sut instanceof ConditionSubInterface).isTrue();
    }

    @Test
    @DisplayName("구현: Condition<T> 구현체로 notCondition을 구현한다.")
    void notConditionImpl() {
        sut = ConditionFactory.createNotCondition(alwaysTrue);

        assertThat(sut instanceof ConditionSubInterface).isTrue();
    }

    interface ConditionSubInterface extends Condition2<Object>{
        @Override
        boolean isSatisfiedBy(Object factor);
    }
}