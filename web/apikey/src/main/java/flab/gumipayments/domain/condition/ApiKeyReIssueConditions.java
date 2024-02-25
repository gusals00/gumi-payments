package flab.gumipayments.domain.condition;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.ApiKeyType.PROD;
import static flab.gumipayments.domain.ApiKeyType.TEST;

@Component
public class ApiKeyReIssueConditions {
    public static final Condition<ReIssueFactor> IS_PROD_API_KEY = factor -> factor.getApiKeyType() == PROD;
    public static final Condition<ReIssueFactor> IS_TEST_API_KEY = factor -> factor.getApiKeyType() == TEST;
    public static final Condition<ReIssueFactor> EXIST_ACCOUNT = ReIssueFactor::isAccountExist;
    public static final Condition<ReIssueFactor> EXIST_API_KEY = ReIssueFactor::isApiKeyExist;
    public static final Condition<ReIssueFactor> IS_CONTRACT_COMPLETE = ReIssueFactor::isContractCompleteExist;

}
