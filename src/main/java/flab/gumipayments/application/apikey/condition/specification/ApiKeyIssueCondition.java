package flab.gumipayments.application.apikey.condition.specification;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;

public interface ApiKeyIssueCondition {

    boolean isSatisfiedBy(ApiKeyIssueCommand command);
    ApiKeyIssueCondition and(ApiKeyIssueCondition other);
    ApiKeyIssueCondition or(ApiKeyIssueCondition other);
    ApiKeyIssueCondition not();

}
