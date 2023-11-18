package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.ApiKeyType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyIssueCandidate {
    private Long accountId;
    private ApiKeyType apiKeyType;
}
