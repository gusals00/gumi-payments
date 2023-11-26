package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.support.specification.Condition;

public class ContractCompleteCondition implements Condition<ApiKeyIssueCommand> {

    public static ContractCompleteCondition contractCompleteCondition(){
        return new ContractCompleteCondition();
    }
    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.isContractCompleteExist();
    }
}