package flab.gumipayments.application.apikey;

import org.springframework.stereotype.Component;

import static flab.gumipayments.application.apikey.ApiKeyIssueConditionFactory.*;

@Component
public class ApiKeyIssueAvailableCheck {
    public boolean test(ApiKeyIssueAvailableCheckRequest issueAvailableCheckRequest) {
        return PRODUCT_API_KEY_ISSUE_CONDITION.or(TEST_API_KEY_ISSUE_CONDITION).test(issueAvailableCheckRequest);
    }

    private static final ApiKeyIssueCondition TEST_API_KEY_ISSUE_CONDITION = new ApiKeyIssueCondition(
            testApiKeyCondition.and(existAccountCondition)
                    .and(existContractCompleteCondition)
                    .and(alreadyExistApiKeyCondition.negate())
    );

    private static final ApiKeyIssueCondition PRODUCT_API_KEY_ISSUE_CONDITION = new ApiKeyIssueCondition(
            productApiKeyCondition.and(existAccountCondition)
                    .and(alreadyExistApiKeyCondition.negate())
    );
}
