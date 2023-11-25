package flab.gumipayments.application.apikey.condition.specification;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;

public class FalseCondition extends CompositeApiKeyIssueCondition {
    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return false;
    }
}
