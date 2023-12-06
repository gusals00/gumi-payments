package flab.gumipayments.support.proxyspecification;

import flab.gumipayments.support.specification.Condition;

import java.util.Arrays;

@FunctionalInterface
public interface Condition2<T> {

    boolean isSatisfiedBy(T factor);

    default Condition2<T> and(Condition2<T> other) {
        return ConditionFactory.createAndCondition(this, other);
    }

    default Condition2<T> or(Condition2<T> other) {
        return ConditionFactory.createOrCondition(this, other);
    }

    default Condition2<T> not() {
        return ConditionFactory.createNotCondition(this);
    }

    static <T> Condition2<T> and(Condition2<T> left, Condition2<T>... right) {
        if (right == null || right.length == 0) {
            return left;
        }
        Condition2[] remain = Arrays.stream(right)
                .toList()
                .subList(1, right.length)
                .toArray(new Condition2[0]);
        return left.and(and(right[0], remain));
    }

    static <T> Condition2<T> or(Condition2<T> left, Condition2<T>... right) {
        if (right == null || right.length == 0) {
            return left;
        }
        Condition2[] remain = Arrays.stream(right)
                .toList()
                .subList(1, right.length)
                .toArray(new Condition2[0]);
        return left.or(or(right[0], remain));
    }

    static <T> Condition2<T> not(Condition2<T> left) {
        return left.not();
    }

}
