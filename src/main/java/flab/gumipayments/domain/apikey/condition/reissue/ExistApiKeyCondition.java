package flab.gumipayments.domain.apikey.condition.reissue;


import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;

public class ExistApiKeyCondition implements ApiKeyReIssueCondition {
    @Override
    public boolean isSatisfiedBy(ApiKeyReIssueCommand command) {
        return command.isApiKeyExist();
    }
}
