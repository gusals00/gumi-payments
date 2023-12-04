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

//    static ApiKeyReIssueCondition and(ApiKeyReIssueCondition... condition2) {
//        ApiKeyReIssueCondition curCondition = command -> true;
//        for (ApiKeyReIssueCondition apiKeyReIssueCondition2 : condition2) {
//            curCondition = curCondition.and(apiKeyReIssueCondition2);
//        }
//        return curCondition;
//    }
//
//    static ApiKeyReIssueCondition or(ApiKeyReIssueCondition... condition) {
//        ApiKeyReIssueCondition curCondition = command -> false;
//        for (ApiKeyReIssueCondition apiKeyReIssueCondition2 : condition) {
//            curCondition = curCondition.or(apiKeyReIssueCondition2);
//        }
//        return curCondition;
//    }
//
//    static ApiKeyReIssueCondition not(ApiKeyReIssueCondition condition) {
//        return condition.not();
//    }
}
