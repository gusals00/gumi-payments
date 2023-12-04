package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;

@FunctionalInterface
public interface ApiKeyIssueCondition extends Condition<IssueFactor> {

    @Override
    boolean isSatisfiedBy(IssueFactor factor);

    @Override
    default ApiKeyIssueCondition and(Condition<IssueFactor> other) {
        return (ApiKeyIssueCondition) factor -> this.isSatisfiedBy(factor) && other.isSatisfiedBy(factor);
    }

    @Override
    default ApiKeyIssueCondition or(Condition<IssueFactor> other) {
        return (ApiKeyIssueCondition) factor -> this.isSatisfiedBy(factor) || other.isSatisfiedBy(factor);
    }

    @Override
    default ApiKeyIssueCondition not() {
        return factor -> !this.isSatisfiedBy(factor);
    }
}
