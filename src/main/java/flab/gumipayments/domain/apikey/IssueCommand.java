package flab.gumipayments.domain.apikey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class IssueCommand {

    private Long accountId;
    private LocalDateTime expireDate;

    private ApiKeyType apiKeyType;
    private boolean accountExist;
    private boolean apiKeyExist;
    private boolean contractCompleteExist;
}
