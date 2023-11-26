package flab.gumipayments.domain.apikey.condition.reissue;


import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.support.specification.Condition;

public class ApiKeyTypeCondition implements Condition<ApiKeyReIssueCommand> {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(ApiKeyReIssueCommand command) {
        return command.getApiKeyType() == apiKeyType;
    }

}