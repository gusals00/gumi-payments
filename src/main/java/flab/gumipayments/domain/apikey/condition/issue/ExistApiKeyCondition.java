package flab.gumipayments.domain.apikey.condition.issue;


import flab.gumipayments.domain.apikey.IssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;

public class ExistApiKeyCondition implements ApiKeyIssueCondition {
    @Override
    public boolean isSatisfiedBy(IssueCommand command) {
        return command.isApiKeyExist();
    }
}
