package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;

import flab.gumipayments.support.specification.Condition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.apikey.ApiKeyType.PROD;
import static flab.gumipayments.domain.apikey.ApiKeyType.TEST;

@Component
public class ApiKeyReIssueConditions {
    public static final Condition<ApiKeyReIssueCommand> IS_PROD_API_KEY = new ApiKeyTypeCondition(PROD);
    public static final Condition<ApiKeyReIssueCommand> IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final Condition<ApiKeyReIssueCommand> EXIST_ACCOUNT = new ExistAccountCondition();
    public static final Condition<ApiKeyReIssueCommand> EXIST_API_KEY = new ExistApiKeyCondition();
    public static final Condition<ApiKeyReIssueCommand> IS_CONTRACT_COMPLETE = new ContractCompleteCondition();

}
