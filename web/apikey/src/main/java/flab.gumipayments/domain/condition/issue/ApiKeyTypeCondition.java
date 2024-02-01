package flab.gumipayments.domain.condition.issue;


import flab.gumipayments.domain.ApiKeyType;
import flab.gumipayments.domain.IssueFactor;
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