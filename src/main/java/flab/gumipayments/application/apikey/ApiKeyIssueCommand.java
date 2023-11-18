package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.ApiKeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApiKeyIssueCommand {

    private Long accountId;
    private ApiKeyType keyType;
    private LocalDateTime expireDate;
}