package flab.gumipayments.domain.apikey.condition.reissue;


import flab.gumipayments.domain.apikey.ReIssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.support.specification.Condition;

public class ApiKeyTypeCondition implements Condition<ReIssueFactor> {
    private ApiKeyType apiKeyType;

    public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
        this.apiKeyType = apiKeyType;
    }

    @Override
    public boolean isSatisfiedBy(ReIssueFactor command) {
        return command.getApiKeyType() == apiKeyType;
    }

}