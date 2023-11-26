package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.support.specification.Condition;
import org.springframework.stereotype.Component;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public final class ApiKeyIssueConditions {
    public static final Condition<ApiKeyIssueCommand> IS_PROD_API_KEY = new ApiKeyTypeCondition(PRODUCTION);
    public static final Condition<ApiKeyIssueCommand> IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final Condition<ApiKeyIssueCommand> EXIST_ACCOUNT = new ExistAccountCondition();
    public static final Condition<ApiKeyIssueCommand> EXIST_API_KEY = new ExistApiKeyCondition();
    public static final Condition<ApiKeyIssueCommand> IS_CONTRACT_COMPLETE = new ContractCompleteCondition();


}
