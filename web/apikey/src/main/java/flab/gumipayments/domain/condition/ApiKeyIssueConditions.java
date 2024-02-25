package flab.gumipayments.domain.condition;

import flab.gumipayments.domain.IssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.ApiKeyType.PROD;
import static flab.gumipayments.domain.ApiKeyType.TEST;

@Component
public final class ApiKeyIssueConditions {
    public static final Condition<IssueFactor> IS_PROD_API_KEY = factor -> factor.getApiKeyType() == PROD;
    public static final Condition<IssueFactor> IS_TEST_API_KEY = factor -> factor.getApiKeyType() == TEST;
    public static final Condition<IssueFactor> EXIST_ACCOUNT = IssueFactor::isAccountExist;
    public static final Condition<IssueFactor> EXIST_API_KEY = IssueFactor::isApiKeyExist;
    public static final Condition<IssueFactor> IS_CONTRACT_COMPLETE = IssueFactor::isContractCompleteExist;

}
