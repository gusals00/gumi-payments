package flab.gumipayments.domain.apikey.condition.issue;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.support.specification.Condition;

public class ApiKeyTypeCondition implements Condition<ApiKeyIssueCommand> {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.getApiKeyType() == apiKeyType;
    }

}