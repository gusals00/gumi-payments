package flab.gumipayments.application.apikey.condition.specification;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;

public class OrCondition extends CompositeApiKeyIssueCondition {
    private ApiKeyIssueCondition left;
    private ApiKeyIssueCondition right;

    public OrCondition(ApiKeyIssueCondition left, ApiKeyIssueCondition right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return left.isSatisfiedBy(command) || right.isSatisfiedBy(command);
    }
}
