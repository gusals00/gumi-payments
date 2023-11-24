package flab.gumipayments.application.apikey.condition;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.condition.specification.CompositeApiKeyIssueCondition;

public class ExistApiKeyCondition extends CompositeApiKeyIssueCondition {
    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.isApiKeyExist();
    }
}
