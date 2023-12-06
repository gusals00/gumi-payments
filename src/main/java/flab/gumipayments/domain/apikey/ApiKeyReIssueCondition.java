package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;

public interface ApiKeyReIssueCondition extends Condition<ReIssueFactor> {
    @Override
    boolean isSatisfiedBy(ReIssueFactor factor);

    @Override
    default ApiKeyReIssueCondition and(Condition<ReIssueFactor> other) {
        return (ApiKeyReIssueCondition) factor -> this.isSatisfiedBy(factor) && other.isSatisfiedBy(factor);
    }

    @Override
    default ApiKeyReIssueCondition or(Condition<ReIssueFactor> other) {
        return (ApiKeyReIssueCondition) factor -> this.isSatisfiedBy(factor) || other.isSatisfiedBy(factor);
    }

    @Override
    default ApiKeyReIssueCondition not() {
        return factor -> !this.isSatisfiedBy(factor);
    }
}
