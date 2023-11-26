package flab.gumipayments.support.specification;


public interface Condition<T> {
    boolean isSatisfiedBy(T command);
    default Condition<T> and(Condition<T> other){
        return command -> this.isSatisfiedBy(command)&& other.isSatisfiedBy(command);
    }
    default Condition<T> or(Condition<T> other){
        return command -> this.isSatisfiedBy(command) || other.isSatisfiedBy(command);
    }
    default Condition<T> not(){
        return command -> !this.isSatisfiedBy(command);
    }

    static<T> Condition<T> and(Condition<T>... condition) {
        Condition<T> curCondition = command -> true;
        for (Condition<T> apiKeyIssueCondition : condition) {
            curCondition = curCondition.and(apiKeyIssueCondition);
        }
        return curCondition;
    }

    static<T> Condition<T> or(Condition<T>... condition) {
        Condition<T> curCondition = command -> false;
        for (Condition<T> apiKeyIssueCondition : condition) {
            curCondition = curCondition.or(apiKeyIssueCondition);
        }
        return curCondition;
    }

    static<T> Condition<T> not(Condition<T> condition) {
        return condition.not();
    }
}
