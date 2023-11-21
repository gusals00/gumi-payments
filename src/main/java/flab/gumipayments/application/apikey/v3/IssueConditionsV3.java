package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.apikey.ApiKeyType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;

@Component
public class IssueConditionsV3 {

    public static class ApiKeyTypeCondition implements Predicate<IssueCandidateV3> {
        private ApiKeyType apiKeyType;

        public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
            this.apiKeyType = apiKeyType;
        }

        @Override
        public boolean test(IssueCandidateV3 issueCandidate) {
            return issueCandidate.getApiKeyType()==apiKeyType;
        }
    }

    public static class ExistAccountCondition implements Predicate<IssueCandidateV3> {
        @Override
        public boolean test(IssueCandidateV3 issueCandidate) {
            return issueCandidate.isAccountExist();
        }
    }

    public static class AlreadyExistApiKeyCondition implements Predicate<IssueCandidateV3> {
        @Override
        public boolean test(IssueCandidateV3 issueCandidate) {
            return issueCandidate.isApiKeyExist();
        }
    }

    public static class ContractCompleteCondition implements Predicate<IssueCandidateV3> {

        @Override
        public boolean test(IssueCandidateV3 issueCandidate) {
            return issueCandidate.isContractCompleteExist();
        }
    }

    public static class ApiKeyIssueConditionV3 implements Predicate<IssueCandidateV3> {
        private Predicate<IssueCandidateV3> predicate;

        public ApiKeyIssueConditionV3(Predicate<IssueCandidateV3> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(IssueCandidateV3 issueCandidate) {
            return predicate.test(issueCandidate);
        }
    }


    private static final Predicate<IssueCandidateV3> productApiKeyCondition = new ApiKeyTypeCondition(PRODUCTION);
    private static final Predicate<IssueCandidateV3> testApiKeyCondition = new ApiKeyTypeCondition(TEST);
    private static final Predicate<IssueCandidateV3> existAccountCondition =new ExistAccountCondition();
    private static final Predicate<IssueCandidateV3> alreadyExistApiKeyCondition = new AlreadyExistApiKeyCondition();
    private static final Predicate<IssueCandidateV3> existContractCompleteCondition = new ContractCompleteCondition();

    private static final ApiKeyIssueConditionV3 TEST_API_KEY_ISSUE_CONDITION = new ApiKeyIssueConditionV3(
            testApiKeyCondition.and(existAccountCondition)
                    .and(existContractCompleteCondition)
                    .and(alreadyExistApiKeyCondition.negate())
    );

    private static final ApiKeyIssueConditionV3 PRODUCT_API_KEY_ISSUE_CONDITION = new ApiKeyIssueConditionV3(
            productApiKeyCondition.and(existAccountCondition)
                    .and(alreadyExistApiKeyCondition.negate())
    );


    public ApiKeyIssueConditionV3 getProductApiKeyIssueCondition(){
        return PRODUCT_API_KEY_ISSUE_CONDITION;
    }
    public ApiKeyIssueConditionV3 getTestApiKeyIssueCondition(){
        return TEST_API_KEY_ISSUE_CONDITION;
    }
}
