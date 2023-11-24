package flab.gumipayments.application.apikey.condition;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.condition.specification.CompositeApiKeyIssueCondition;
import flab.gumipayments.domain.apikey.ApiKeyType;

public class ApiKeyTypeCondition extends CompositeApiKeyIssueCondition {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.getApiKeyType() == apiKeyType;
    }

}