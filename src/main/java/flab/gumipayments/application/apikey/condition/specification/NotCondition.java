package flab.gumipayments.application.apikey.condition.specification;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;

public class NotCondition extends CompositeApiKeyIssueCondition {

    private ApiKeyIssueCondition other;

    public NotCondition(ApiKeyIssueCondition other) {
        this.other = other;
    }

    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return !other.isSatisfiedBy(command);
    }

}
