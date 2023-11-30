package flab.gumipayments.domain.apikey.condition.issue;


import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;

public class ApiKeyTypeCondition implements ApiKeyIssueCondition {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.getApiKeyType() == apiKeyType;
    }

}