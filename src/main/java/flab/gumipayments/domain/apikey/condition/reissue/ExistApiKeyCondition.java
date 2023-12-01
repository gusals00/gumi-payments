package flab.gumipayments.domain.apikey.condition.reissue;


import flab.gumipayments.domain.apikey.ReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;

public class ExistApiKeyCondition implements ApiKeyReIssueCondition {
    @Override
    public boolean isSatisfiedBy(ReIssueCommand command) {
        return command.isApiKeyExist();
    }
}
