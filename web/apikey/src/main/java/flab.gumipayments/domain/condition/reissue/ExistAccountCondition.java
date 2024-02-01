package flab.gumipayments.domain.condition.reissue;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;

public class ExistAccountCondition implements Condition<ReIssueFactor> {
    @Override
    public boolean isSatisfiedBy(ReIssueFactor command) {
        return command.isAccountExist();
    }
}
