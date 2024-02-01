package flab.gumipayments.infrastructure;

import flab.gumipayments.domain.ApiKeyType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiKeyInfo {
    private Long apiKeyId;
    private ApiKeyType type;
}
