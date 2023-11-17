package flab.gumipayments.application.apiKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ApiKeyRenewCommand {

    private Long apiKeyId;
    private LocalDateTime ExpireDate;

}
