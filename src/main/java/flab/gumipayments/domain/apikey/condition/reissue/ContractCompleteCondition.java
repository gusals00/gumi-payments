package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.support.specification.Condition;

public class ContractCompleteCondition implements Condition<ApiKeyReIssueCommand> {

    public static ContractCompleteCondition contractCompleteCondition(){
        return new ContractCompleteCondition();
    }
    @Override
    public boolean isSatisfiedBy(ApiKeyReIssueCommand command) {
        return command.isContractCompleteExist();
    }
}