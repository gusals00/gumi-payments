package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.ApiKeyType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public class ApiKeyIssueConditions {
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> isProductApiKey = new ApiKeyTypeCondition(PRODUCTION);
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> isTestApiKey = new ApiKeyTypeCondition(TEST);
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> existAccount = new ExistAccountCondition();
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> existApiKey = new ExistApiKeyCondition();
    public static final Predicate<ApiKeyIssueAvailableCheckRequest> isContractComplete = new ContractCompleteCondition();

    public static class ApiKeyTypeCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        private ApiKeyType apiKeyType;

        public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
            this.apiKeyType = apiKeyType;
        }

        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest request) {
            return request.getApiKeyType() == apiKeyType;
        }
    }

    public static class ExistAccountCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest request) {
            return request.isAccountExist();
        }
    }

    public static class ExistApiKeyCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest request) {
            return request.isApiKeyExist();
        }
    }

    public static class ContractCompleteCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest request) {
            return request.isContractCompleteExist();
        }
    }

    public static class ApiKeyIssueCondition implements Predicate<ApiKeyIssueAvailableCheckRequest> {
        private Predicate<ApiKeyIssueAvailableCheckRequest> condition;

        public ApiKeyIssueCondition(Predicate<ApiKeyIssueAvailableCheckRequest> condition) {
            this.condition = condition;
        }

        @Override
        public boolean test(ApiKeyIssueAvailableCheckRequest request) {
            return condition.test(request);
        }
    }
}
