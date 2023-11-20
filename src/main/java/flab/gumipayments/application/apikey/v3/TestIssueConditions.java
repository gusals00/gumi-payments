package flab.gumipayments.application.apikey.v3;


import flab.gumipayments.domain.apikey.ApiKeyType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public class TestIssueConditions {

    public static class ApiKeyTypeCondition implements Predicate<TestIssueCandidate> {
        private ApiKeyType apiKeyType;

        public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
            this.apiKeyType = apiKeyType;
        }

        @Override
        public boolean test(TestIssueCandidate issueCandidate) {
            return issueCandidate.getApiKeyType()==apiKeyType;
        }
    }

    public static class ExistAccountCondition implements Predicate<TestIssueCandidate> {
        @Override
        public boolean test(TestIssueCandidate issueCandidate) {
            return issueCandidate.isAccountExist();
        }
    }

    public static class AlreadyExistApiKeyCondition implements Predicate<TestIssueCandidate> {
        @Override
        public boolean test(TestIssueCandidate issueCandidate) {
            return issueCandidate.isApiKeyExist();
        }
    }

    public static class TestApiKeyIssueCondition implements Predicate<TestIssueCandidate> {
        private Predicate<TestIssueCandidate> predicate;

        public TestApiKeyIssueCondition(Predicate<TestIssueCandidate> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(TestIssueCandidate issueCandidate) {
            return predicate.test(issueCandidate);
        }
    }

    private static final Predicate<TestIssueCandidate> testApiKeyCondition = new ApiKeyTypeCondition(TEST);
    private static final Predicate<TestIssueCandidate> existAccountCondition =new ExistAccountCondition();
    private static final Predicate<TestIssueCandidate> alreadyExistApiKeyCondition = new AlreadyExistApiKeyCondition();


    private static final TestApiKeyIssueCondition TEST_API_KEY_ISSUE_CONDITION = new TestApiKeyIssueCondition(
            testApiKeyCondition.and(existAccountCondition).and(alreadyExistApiKeyCondition.negate())
    );

    public TestApiKeyIssueCondition getTestApiKeyIssueCondition(){
        return TEST_API_KEY_ISSUE_CONDITION;
    }

}
