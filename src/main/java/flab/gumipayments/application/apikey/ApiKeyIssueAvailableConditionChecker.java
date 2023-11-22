package flab.gumipayments.application.apikey;

import org.springframework.stereotype.Component;

import static flab.gumipayments.application.apikey.ApiKeyIssueConditions.*;

@Component
public class ApiKeyIssueAvailableConditionChecker {
    public boolean check(ApiKeyIssueAvailableCheckRequest issueAvailableCheckRequest) {
        return productApiKeyIssueCondition
                .or(testApiKeyIssueCondition)
                .test(issueAvailableCheckRequest);
    }

    private static final ApiKeyIssueCondition testApiKeyIssueCondition = new ApiKeyIssueCondition(
            isTestApiKey
                    .and(existAccount)
                    .and(isContractComplete)
                    .and(existApiKey.negate())
    );

    private static final ApiKeyIssueCondition productApiKeyIssueCondition = new ApiKeyIssueCondition(
            isProductApiKey
                    .and(existAccount)
                    .and(existApiKey.negate())
    );
}
