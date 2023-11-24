package flab.gumipayments.application.apikey.condition;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.condition.specification.CompositeApiKeyIssueCondition;

public class ExistAccountCondition extends CompositeApiKeyIssueCondition {
    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.isAccountExist();
    }
}
