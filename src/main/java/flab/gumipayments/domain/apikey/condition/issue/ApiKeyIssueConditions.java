package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public final class ApiKeyIssueConditions {
    public static final Condition<IssueFactor> IS_PROD_API_KEY = new ApiKeyTypeCondition(PROD);
    public static final Condition<IssueFactor> IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final Condition<IssueFactor> EXIST_ACCOUNT = new ExistAccountCondition();
    public static final Condition<IssueFactor> EXIST_API_KEY = new ExistApiKeyCondition();
    public static final Condition<IssueFactor> IS_CONTRACT_COMPLETE = new ContractCompleteCondition();


}
