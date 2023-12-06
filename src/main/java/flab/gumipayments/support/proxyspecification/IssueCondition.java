package flab.gumipayments.support.proxyspecification;

import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.support.proxyspecification.Condition2;

@FunctionalInterface
public interface IssueCondition extends Condition2<IssueFactor> {
    @Override
    boolean isSatisfiedBy(IssueFactor factor);
}
