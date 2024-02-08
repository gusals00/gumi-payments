package flab.gumipayments.infrastructure.kafka;

import flab.gumipayments.domain.ApiKeyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiKeyMessageDTO {

    private Long id;
    private String secretKey;
    private String clientKey;
    private Long accountId;
    private ApiKeyType type;
    private LocalDateTime expireDate;
    private long count;

    @Builder

    public ApiKeyMessageDTO(Long id, String secretKey, String clientKey, Long accountId, ApiKeyType type, LocalDateTime expireDate, long count) {
        this.id = id;
        this.secretKey = secretKey;
        this.clientKey = clientKey;
        this.accountId = accountId;
        this.type = type;
        this.expireDate = expireDate;
        this.count = count;
    }
}
