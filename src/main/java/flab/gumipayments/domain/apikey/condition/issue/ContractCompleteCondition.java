package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;

public class ContractCompleteCondition implements ApiKeyIssueCondition {

    @Override
    public boolean isSatisfiedBy(IssueFactor command) {
        return command.isContractCompleteExist();
    }
}