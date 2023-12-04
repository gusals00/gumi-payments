package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ReIssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;

public class ContractCompleteCondition implements ApiKeyReIssueCondition {

    public static ContractCompleteCondition contractCompleteCondition(){
        return new ContractCompleteCondition();
    }
    @Override
    public boolean isSatisfiedBy(ReIssueFactor command) {
        return command.isContractCompleteExist();
    }
}