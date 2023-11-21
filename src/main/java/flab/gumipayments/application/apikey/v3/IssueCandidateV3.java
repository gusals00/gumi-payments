package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.apikey.ApiKeyType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IssueCandidateV3 {
    private ApiKeyType apiKeyType;
    private boolean accountExist;
    private boolean apiKeyExist;
    private boolean contractCompleteExist;
}
