package flab.gumipayments.infrastructure.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApiKeyMessageDTO {
    private String name;

    @Builder
    public ApiKeyMessageDTO(String name) {
        this.name = name;
    }
}
