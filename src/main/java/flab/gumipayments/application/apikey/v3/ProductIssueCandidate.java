package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.Contract;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductIssueCandidate {
    private ApiKeyType apiKeyType;
    private boolean accountExist;
    private boolean apiKeyExist;
    private Contract contract;
}
