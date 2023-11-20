package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.contract.ContractStatus;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static flab.gumipayments.domain.contract.ContractStatus.CONTRACT_COMPLETE;

@Component
public class IssueConditions {

    public static class ExistAccountCondition implements Predicate<IssueCandidate> {
        @Override
        public boolean test(IssueCandidate issueCandidate) {
            return issueCandidate.isAccountExist();
        }
    }

    public static class ContractCondition implements Predicate<IssueCandidate> {

        private ContractStatus expectedContractStatus;

        public ContractCondition(ContractStatus expectedContractStatus) {
            this.expectedContractStatus = expectedContractStatus;
        }

        @Override
        public boolean test(IssueCandidate issueCandidate) {
            return issueCandidate.getContract().getStatus() == expectedContractStatus;
        }
    }

    public static class AlreadyExistApiKeyCondition implements Predicate<IssueCandidate> {
        @Override
        public boolean test(IssueCandidate issueCandidate) {
            return issueCandidate.isApiKeyExist();
        }
    }

    public static class IssueCondition implements Predicate<IssueCandidate> {
        private Predicate<IssueCandidate> predicate;

        public IssueCondition(Predicate<IssueCandidate> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(IssueCandidate issueCandidate) {
            return predicate.test(issueCandidate);
        }
    }

    private static final Predicate<IssueCandidate> existAccountCondition = new ExistAccountCondition();
    private static final Predicate<IssueCandidate> contractCompleteCondition = new ContractCondition(CONTRACT_COMPLETE);
    private static final Predicate<IssueCandidate> alreadyExistApiKeyCondition = new AlreadyExistApiKeyCondition();

    private static final IssueCondition testIssueCondition = new IssueCondition(
            existAccountCondition
                    .and(alreadyExistApiKeyCondition.negate()));
    private static final IssueCondition productIssueCondition = new IssueCondition(existAccountCondition
            .and(contractCompleteCondition)
            .and(alreadyExistApiKeyCondition.negate()));

    public IssueCondition getTestIssueCondition(){
        return testIssueCondition;
    }

    public IssueCondition getProductIssueCondition(){
        return productIssueCondition;
    }
}
