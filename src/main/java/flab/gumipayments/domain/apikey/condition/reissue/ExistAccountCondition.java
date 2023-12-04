package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ReIssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;

public class ExistAccountCondition implements ApiKeyReIssueCondition {
    @Override
    public boolean isSatisfiedBy(ReIssueFactor command) {
        return command.isAccountExist();
    }
}
