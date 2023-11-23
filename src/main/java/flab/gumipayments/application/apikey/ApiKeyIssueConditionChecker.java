package flab.gumipayments.application.apikey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyIssueConditionChecker {

    private final ApiKeyIssueConditions issueConditions;

    public boolean check(ApiKeyIssueCheckRequest issueCheckRequest) {
        return issueConditions.apiKeyIssueCondition().test(issueCheckRequest);
    }
}
