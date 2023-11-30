package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public final class ApiKeyIssueConditions {
    public static final ApiKeyIssueCondition IS_PROD_API_KEY = new ApiKeyTypeCondition(PROD);
    public static final ApiKeyIssueCondition IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final ApiKeyIssueCondition EXIST_ACCOUNT = new ExistAccountCondition();
    public static final ApiKeyIssueCondition EXIST_API_KEY = new ExistApiKeyCondition();
    public static final ApiKeyIssueCondition IS_CONTRACT_COMPLETE = new ContractCompleteCondition();


}
