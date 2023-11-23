package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.ApiKeyType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public class ApiKeyIssueConditions {
    private static final Predicate<ApiKeyIssueCheckRequest> isProductApiKey = new ApiKeyTypeCondition(PRODUCTION);
    private static final Predicate<ApiKeyIssueCheckRequest> isTestApiKey = new ApiKeyTypeCondition(TEST);
    private static final Predicate<ApiKeyIssueCheckRequest> existAccount = new ExistAccountCondition();
    private static final Predicate<ApiKeyIssueCheckRequest> existApiKey = new ExistApiKeyCondition();
    private static final Predicate<ApiKeyIssueCheckRequest> isContractComplete = new ContractCompleteCondition();

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

    private static final ApiKeyIssueCondition apiKeyIssueCondition = new ApiKeyIssueCondition(
            testApiKeyIssueCondition.or(productApiKeyIssueCondition)
    );

    public ApiKeyIssueCondition apiKeyIssueCondition() {
        return apiKeyIssueCondition;
    }

    public static class ApiKeyTypeCondition implements Predicate<ApiKeyIssueCheckRequest> {
        private ApiKeyType apiKeyType;

        public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
            this.apiKeyType = apiKeyType;
        }

        @Override
        public boolean test(ApiKeyIssueCheckRequest request) {
            return request.getApiKeyType() == apiKeyType;
        }
    }

    public static class ExistAccountCondition implements Predicate<ApiKeyIssueCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueCheckRequest request) {
            return request.isAccountExist();
        }
    }

    public static class ExistApiKeyCondition implements Predicate<ApiKeyIssueCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueCheckRequest request) {
            return request.isApiKeyExist();
        }
    }

    public static class ContractCompleteCondition implements Predicate<ApiKeyIssueCheckRequest> {
        @Override
        public boolean test(ApiKeyIssueCheckRequest request) {
            return request.isContractCompleteExist();
        }
    }

    public static class ApiKeyIssueCondition implements Predicate<ApiKeyIssueCheckRequest> {
        private Predicate<ApiKeyIssueCheckRequest> condition;

        public ApiKeyIssueCondition(Predicate<ApiKeyIssueCheckRequest> condition) {
            this.condition = condition;
        }

        @Override
        public boolean test(ApiKeyIssueCheckRequest request) {
            return condition.test(request);
        }
    }

}
