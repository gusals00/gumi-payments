package flab.gumipayments.application.apikey;

@FunctionalInterface
public interface ApiKeyIssueCondition {

    boolean isSatisfiedBy(ApiKeyIssueCandidate candidate);
    default ApiKeyIssueCondition and(ApiKeyIssueCondition other) {
        return candidate -> isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate);
    }

    default ApiKeyIssueCondition not(){
        return candidate -> !isSatisfiedBy(candidate);
    }
}
