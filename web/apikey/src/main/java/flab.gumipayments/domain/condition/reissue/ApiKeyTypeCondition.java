package flab.gumipayments.domain.condition.reissue;


import flab.gumipayments.domain.ApiKeyType;
import flab.gumipayments.domain.ReIssueFactor;
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