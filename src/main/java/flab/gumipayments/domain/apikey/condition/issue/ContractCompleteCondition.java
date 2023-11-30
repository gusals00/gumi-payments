package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;

public class ContractCompleteCondition implements ApiKeyIssueCondition {

    @Override
    public boolean isSatisfiedBy(ApiKeyIssueCommand command) {
        return command.isContractCompleteExist();
    }
}