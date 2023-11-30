package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;

@FunctionalInterface
public interface ApiKeyIssueCondition extends Condition<ApiKeyIssueCommand> {

    @Override
    boolean isSatisfiedBy(ApiKeyIssueCommand command);

    @Override
    default ApiKeyIssueCondition and(Condition<ApiKeyIssueCommand> other) {
        return (ApiKeyIssueCondition) command -> this.isSatisfiedBy(command) && other.isSatisfiedBy(command);
    }

    @Override
    default ApiKeyIssueCondition or(Condition<ApiKeyIssueCommand> other) {
        return (ApiKeyIssueCondition) command -> this.isSatisfiedBy(command) || other.isSatisfiedBy(command);
    }

    @Override
    default ApiKeyIssueCondition not() {
        return command -> !this.isSatisfiedBy(command);
    }

    static ApiKeyIssueCondition and(ApiKeyIssueCondition... condition2) {
        ApiKeyIssueCondition curCondition = command -> true;
        for (ApiKeyIssueCondition apiKeyIssueCondition2 : condition2) {
            curCondition = curCondition.and(apiKeyIssueCondition2);
        }
        return curCondition;
    }

    static ApiKeyIssueCondition or(ApiKeyIssueCondition... condition) {
        ApiKeyIssueCondition curCondition = command -> false;
        for (ApiKeyIssueCondition apiKeyIssueCondition2 : condition) {
            curCondition = curCondition.or(apiKeyIssueCondition2);
        }
        return curCondition;
    }

    static ApiKeyIssueCondition not(ApiKeyIssueCondition condition) {
        return condition.not();
    }
}
