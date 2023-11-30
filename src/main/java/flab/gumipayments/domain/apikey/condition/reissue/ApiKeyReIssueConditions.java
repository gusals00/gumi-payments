package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.apikey.ApiKeyType.PROD;
import static flab.gumipayments.domain.apikey.ApiKeyType.TEST;

@Component
public class ApiKeyReIssueConditions {
    public static final ApiKeyReIssueCondition IS_PROD_API_KEY = new ApiKeyTypeCondition(PROD);
    public static final ApiKeyReIssueCondition IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final ApiKeyReIssueCondition EXIST_ACCOUNT = new ExistAccountCondition();
    public static final ApiKeyReIssueCondition EXIST_API_KEY = new ExistApiKeyCondition();
    public static final ApiKeyReIssueCondition IS_CONTRACT_COMPLETE = new ContractCompleteCondition();

}