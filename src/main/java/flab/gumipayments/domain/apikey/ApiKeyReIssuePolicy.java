package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;
import lombok.Getter;

@Getter
public class ApiKeyReIssuePolicy {

    private Condition<ReIssueFactor> conditions;

    public static ApiKeyReIssuePolicy of(Condition<ReIssueFactor> conditions){
        return new ApiKeyReIssuePolicy(conditions);
    }

    ApiKeyReIssuePolicy(Condition<ReIssueFactor> conditions){
        this.conditions = conditions;
    }

    public boolean check(ReIssueFactor factor){
        return getConditions().isSatisfiedBy(factor);
    }
}
