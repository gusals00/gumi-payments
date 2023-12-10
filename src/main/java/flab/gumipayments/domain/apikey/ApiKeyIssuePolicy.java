package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;
import lombok.Getter;


@Getter
public class ApiKeyIssuePolicy {

    private Condition<IssueFactor> conditions;

    public static ApiKeyIssuePolicy of(Condition<IssueFactor> conditions){
        return new ApiKeyIssuePolicy(conditions);
    }

    ApiKeyIssuePolicy(Condition<IssueFactor> conditions) {
        this.conditions = conditions;
    }

    public boolean check(IssueFactor factor){
        return getConditions().isSatisfiedBy(factor);
    }
}
