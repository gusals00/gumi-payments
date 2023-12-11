package flab.gumipayments.domain.apikey.condition.issue;


import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.support.specification.Condition;

public class ApiKeyTypeCondition implements Condition<IssueFactor> {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(IssueFactor command) {
        return command.getApiKeyType() == apiKeyType;
    }

}