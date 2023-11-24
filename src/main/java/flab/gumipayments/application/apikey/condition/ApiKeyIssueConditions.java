package flab.gumipayments.application.apikey.condition;

import flab.gumipayments.application.apikey.condition.ApiKeyTypeCondition;
import flab.gumipayments.application.apikey.condition.ContractCompleteCondition;
import flab.gumipayments.application.apikey.condition.ExistAccountCondition;
import flab.gumipayments.application.apikey.condition.ExistApiKeyCondition;
import flab.gumipayments.application.apikey.condition.specification.ApiKeyIssueCondition;
import flab.gumipayments.domain.apikey.ApiKeyType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public final class ApiKeyIssueConditions {
    public static final ApiKeyIssueCondition IS_PROD_API_KEY = new ApiKeyTypeCondition(PRODUCTION);
    public static final ApiKeyIssueCondition IS_TEST_API_KEY = new ApiKeyTypeCondition(TEST);
    public static final ApiKeyIssueCondition EXIST_ACCOUNT = new ExistAccountCondition();
    public static final ApiKeyIssueCondition EXIST_API_KEY = new ExistApiKeyCondition();
    public static final ApiKeyIssueCondition IS_CONTRACT_COMPLETE = new ContractCompleteCondition();


}
