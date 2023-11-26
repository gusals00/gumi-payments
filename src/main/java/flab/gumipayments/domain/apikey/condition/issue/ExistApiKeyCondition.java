package flab.gumipayments.domain.apikey.condition.issue;


import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.support.specification.Condition;

public class ExistApiKeyCondition implements Condition<ApiKeyIssueCommand> {
    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.isApiKeyExist();
    }
}
