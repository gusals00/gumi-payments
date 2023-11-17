package flab.gumipayments.application.apiKey;

import flab.gumipayments.domain.apiKey.ApiKeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApiKeyReIssueCommand {

    private Long accountId;
    private ApiKeyType keyType;
    private LocalDateTime expireDate;
}

