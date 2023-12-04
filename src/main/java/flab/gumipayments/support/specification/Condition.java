package flab.gumipayments.support.specification;

@FunctionalInterface
public interface Condition<T> {
    boolean isSatisfiedBy(T factor);
    default Condition<T> and(Condition<T> other){
        return command -> this.isSatisfiedBy(command)&& other.isSatisfiedBy(command);
    }
    default Condition<T> or(Condition<T> other){
        return command -> this.isSatisfiedBy(command) || other.isSatisfiedBy(command);
    }
    default Condition<T> not(){
        return command -> !this.isSatisfiedBy(command);
    }

}
