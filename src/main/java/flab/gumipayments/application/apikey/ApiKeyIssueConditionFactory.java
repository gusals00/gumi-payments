package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.ApiKeyType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public class ApiKeyIssueConditionFactory {
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> productApiKeyCondition = new ApiKeyTypeCondition(PRODUCTION);
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> testApiKeyCondition = new ApiKeyTypeCondition(TEST);
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> existAccountCondition = new ExistAccountCondition();
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> alreadyExistApiKeyCondition = new AlreadyExistApiKeyCondition();
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> existContractCompleteCondition = new ContractCompleteCondition();

    public static class ApiKeyTypeCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        private ApiKeyType apiKeyType;

        public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
            this.apiKeyType = apiKeyType;
        }

        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest issueCandidate) {
            return issueCandidate.getApiKeyType() == apiKeyType;
        }
    }

    public static class ExistAccountCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest issueCandidate) {
            return issueCandidate.isAccountExist();
        }
    }

    public static class AlreadyExistApiKeyCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest issueCandidate) {
            return issueCandidate.isApiKeyExist();
        }
    }

    public static class ContractCompleteCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest issueCandidate) {
            return issueCandidate.isContractCompleteExist();
        }
    }

    public static class ApiKeyIssueCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        private Predicate<ApiKeyIssueAvailableCheckRequest> predicate;

        public ApiKeyIssueCondition(Predicate<ApiKeyIssueAvailableCheckRequest> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest issueCandidate) {
            return predicate.test(issueCandidate);
        }
    }
}
