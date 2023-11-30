package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;

public class ContractCompleteCondition implements ApiKeyReIssueCondition {

    public static ContractCompleteCondition contractCompleteCondition(){
        return new ContractCompleteCondition();
    }
    @Override
    public boolean isSatisfiedBy(ApiKeyReIssueCommand command) {
        return command.isContractCompleteExist();
    }
}