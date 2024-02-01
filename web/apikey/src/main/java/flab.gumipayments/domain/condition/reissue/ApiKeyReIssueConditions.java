package flab.gumipayments.domain.condition.reissue;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.ApiKeyType.PROD;
import static flab.gumipayments.domain.ApiKeyType.TEST;

@Component
public class ApiKeyReIssueConditions {
    public static final Condition<ReIssueFactor> IS_PROD_API_KEY = new ApiKeyTypeCondition(PROD);
    public static final Condition<ReIssueFactor> IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final Condition<ReIssueFactor> EXIST_ACCOUNT = new ExistAccountCondition();
    public static final Condition<ReIssueFactor> EXIST_API_KEY = new ExistApiKeyCondition();
    public static final Condition<ReIssueFactor> IS_CONTRACT_COMPLETE = new ContractCompleteCondition();

}
