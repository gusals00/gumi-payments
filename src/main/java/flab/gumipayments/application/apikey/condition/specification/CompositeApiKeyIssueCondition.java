package flab.gumipayments.application.apikey.condition.specification;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;

public abstract class CompositeApiKeyIssueCondition implements ApiKeyIssueCondition {

    private static final ApiKeyIssueCondition TRUE_CONDITION = new CompositeApiKeyIssueCondition() {
        @Override
        public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
            return true;
        }
    };

    private static final ApiKeyIssueCondition FALSE_CONDITION = new CompositeApiKeyIssueCondition() {
        @Override
        public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
            return false;
        }
    };

    @Override
    public ApiKeyIssueCondition and(ApiKeyIssueCondition other) {
        return new AndCondition(this, other);
    }

    @Override
    public ApiKeyIssueCondition or(ApiKeyIssueCondition other) {
        return new OrCondition(this, other);
    }

    @Override
    public ApiKeyIssueCondition not() {
        return new NotCondition(this);
    }

    public static ApiKeyIssueCondition and(ApiKeyIssueCondition... condition) {
        ApiKeyIssueCondition curCondition = TRUE_CONDITION;
        for (ApiKeyIssueCondition apiKeyIssueCondition : condition) {
            curCondition = curCondition.and(apiKeyIssueCondition);
        }
        return curCondition;
    }

    public static ApiKeyIssueCondition or(ApiKeyIssueCondition... condition) {
        ApiKeyIssueCondition curCondition = FALSE_CONDITION;
        for (ApiKeyIssueCondition apiKeyIssueCondition : condition) {
            curCondition = curCondition.or(apiKeyIssueCondition);
        }
        return curCondition;
    }

    public static ApiKeyIssueCondition not(ApiKeyIssueCondition condition) {
        return condition.not();
    }
}
