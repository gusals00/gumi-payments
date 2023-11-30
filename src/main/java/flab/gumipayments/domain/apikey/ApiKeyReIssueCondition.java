package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;

public interface ApiKeyReIssueCondition extends Condition<ApiKeyReIssueCommand> {
    @Override
    boolean isSatisfiedBy(ApiKeyReIssueCommand command);

    @Override
    default ApiKeyReIssueCondition and(Condition<ApiKeyReIssueCommand> other) {
        return (ApiKeyReIssueCondition) command -> this.isSatisfiedBy(command) && other.isSatisfiedBy(command);
    }

    @Override
    default ApiKeyReIssueCondition or(Condition<ApiKeyReIssueCommand> other) {
        return (ApiKeyReIssueCondition) command -> this.isSatisfiedBy(command) || other.isSatisfiedBy(command);
    }

    @Override
    default ApiKeyReIssueCondition not() {
        return command -> !this.isSatisfiedBy(command);
    }

    static ApiKeyReIssueCondition and(ApiKeyReIssueCondition... condition2) {
        ApiKeyReIssueCondition curCondition = command -> true;
        for (ApiKeyReIssueCondition apiKeyReIssueCondition2 : condition2) {
            curCondition = curCondition.and(apiKeyReIssueCondition2);
        }
        return curCondition;
    }

    static ApiKeyReIssueCondition or(ApiKeyReIssueCondition... condition) {
        ApiKeyReIssueCondition curCondition = command -> false;
        for (ApiKeyReIssueCondition apiKeyReIssueCondition2 : condition) {
            curCondition = curCondition.or(apiKeyReIssueCondition2);
        }
        return curCondition;
    }

    static ApiKeyReIssueCondition not(ApiKeyReIssueCondition condition) {
        return condition.not();
    }
}
