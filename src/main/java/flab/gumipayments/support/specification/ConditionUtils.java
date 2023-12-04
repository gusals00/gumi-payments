package flab.gumipayments.support.specification;

import java.util.Arrays;

public class ConditionUtils<T, C> {

    public static <C extends Condition<T>, T> C and(C left, C... right) {
        if (right == null || right.length==0) {
            return left;
        }
        Condition[] remain = Arrays.stream(right)
                .toList()
                .subList(1, right.length)
                .toArray(new Condition[0]);
        return (C)left.and(and(right[0],remain));
    }

    public static <C extends Condition<T>, T> C or(C left, C... right) {
        if (right == null || right.length==0) {
            return left;
        }
        Condition[] remain = Arrays.stream(right)
                .toList()
                .subList(1, right.length)
                .toArray(new Condition[0]);
        return (C)left.or(or(right[0],remain));
    }

    public static <C extends Condition<T>, T> C not(C left) {
        return (C)left.not();
    }
}
