package flab.gumipayments.domain.apikey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ApiKeyCreateCommand {

    private String secretKey;
    private ApiKeyType apiKeyType;
    private LocalDateTime expireDate;
    private Long accountId;
}
