package flab.gumipayments.support.specification;


import java.util.Arrays;

@FunctionalInterface
public interface Condition<T> {
    boolean isSatisfiedBy(T factor);

    default Condition<T> and(Condition<T> other) {
        return command -> this.isSatisfiedBy(command) && other.isSatisfiedBy(command);
    }

    default Condition<T> or(Condition<T> other) {
        return command -> this.isSatisfiedBy(command) || other.isSatisfiedBy(command);
    }

    default Condition<T> not() {
        return command -> !this.isSatisfiedBy(command);
    }

    static <T> Condition<T> and(Condition<T> left, Condition<T>... right) {
        if (right == null || right.length == 0) {
            return left;
        }
        Condition[] remain = Arrays.stream(right)
                .toList()
                .subList(1, right.length)
                .toArray(new Condition[0]);
        return left.and(and(right[0], remain));
    }

    static <T> Condition<T> or(Condition<T> left, Condition<T>... right) {
        if (right == null || right.length == 0) {
            return left;
        }
        Condition[] remain = Arrays.stream(right)
                .toList()
                .subList(1, right.length)
                .toArray(new Condition[0]);
        return left.or(or(right[0], remain));
    }

    static <T> Condition<T> not(Condition<T> left) {
        return left.not();
    }

}
