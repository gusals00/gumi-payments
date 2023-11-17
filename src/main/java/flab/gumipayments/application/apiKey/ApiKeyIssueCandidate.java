package flab.gumipayments.application.apiKey;

import flab.gumipayments.domain.apiKey.ApiKeyType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyIssueCandidate {
    private Long accountId;
    private ApiKeyType apiKeyType;
}
