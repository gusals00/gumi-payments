package flab.gumipayments.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AcceptCommand {
    private String signupKey;
}
