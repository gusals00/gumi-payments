package flab.gumipayments.domain.apikey.condition.reissue;


import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.support.specification.Condition;

public class ExistApiKeyCondition implements Condition<ApiKeyReIssueCommand> {
    @Override
    public boolean isSatisfiedBy(ApiKeyReIssueCommand command) {
        return command.isApiKeyExist();
    }
}
