package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.ContractStatus;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.apikey.ApiKeyType.*;
import static flab.gumipayments.domain.contract.ContractStatus.CONTRACT_COMPLETE;

@Component
public class ProductIssueConditions {

    public static class ApiKeyTypeCondition implements Predicate<ProductIssueCandidate> {
        private ApiKeyType apiKeyType;

        public ApiKeyTypeCondition(ApiKeyType apiKeyType) {
            this.apiKeyType = apiKeyType;
        }

        @Override
        public boolean test(ProductIssueCandidate issueCandidate) {
            return issueCandidate.getApiKeyType()==apiKeyType;
        }
    }

    public static class ExistAccountCondition implements Predicate<ProductIssueCandidate> {
        @Override
        public boolean test(ProductIssueCandidate issueCandidate) {
            return issueCandidate.isAccountExist();
        }
    }

    public static class AlreadyExistApiKeyCondition implements Predicate<ProductIssueCandidate> {
        @Override
        public boolean test(ProductIssueCandidate issueCandidate) {
            return issueCandidate.isApiKeyExist();
        }
    }

    public static class ProductApiKeyIssueCondition implements Predicate<ProductIssueCandidate> {
        private Predicate<ProductIssueCandidate> predicate;

        public ProductApiKeyIssueCondition(Predicate<ProductIssueCandidate> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(ProductIssueCandidate issueCandidate) {
            return predicate.test(issueCandidate);
        }
    }

    public static class ContractCondition implements Predicate<ProductIssueCandidate> {

        private ContractStatus expectedContractStatus;

        public ContractCondition(ContractStatus expectedContractStatus) {
            this.expectedContractStatus = expectedContractStatus;
        }

        @Override
        public boolean test(ProductIssueCandidate issueCandidate) {
            return issueCandidate.getContract().getStatus() == expectedContractStatus;
        }
    }

    private static final Predicate<ProductIssueCandidate> productApiKeyCondition = new ApiKeyTypeCondition(PRODUCTION);
    private static final Predicate<ProductIssueCandidate> existAccountCondition =new ExistAccountCondition();
    private static final Predicate<ProductIssueCandidate> alreadyExistApiKeyCondition = new AlreadyExistApiKeyCondition();
    private static final Predicate<ProductIssueCandidate> contractCompleteCondition = new ContractCondition(CONTRACT_COMPLETE);

    private static final ProductApiKeyIssueCondition PRODUCT_API_KEY_ISSUE_CONDITION = new ProductApiKeyIssueCondition(
            productApiKeyCondition.and(existAccountCondition).and(contractCompleteCondition).and(alreadyExistApiKeyCondition.negate())
    );

    public ProductApiKeyIssueCondition getProductApiKeyIssueCondition(){
        return PRODUCT_API_KEY_ISSUE_CONDITION;
    }
}
