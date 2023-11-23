package flab.gumipayments.application.apikey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static flab.gumipayments.application.apikey.ApiKeyIssueConditions.*;

@Component
@RequiredArgsConstructor
public class ApiKeyIssueAvailableConditionChecker {

    private final ApiKeyIssueConditions issueConditions;

    public boolean check(ApiKeyIssueAvailableCheckRequest issueAvailableCheckRequest) {
        return issueConditions.apiKeyIssueCondition().test(issueAvailableCheckRequest);
    }
}
