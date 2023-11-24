package flab.gumipayments.application.apikey.condition;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.condition.specification.CompositeApiKeyIssueCondition;

public class ContractCompleteCondition extends CompositeApiKeyIssueCondition {

    public static ContractCompleteCondition contractCompleteCondition(){
        return new ContractCompleteCondition();
    }
    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.isContractCompleteExist();
    }
}