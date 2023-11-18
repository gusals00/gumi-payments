package flab.gumipayments.application.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class AcceptCommand {
    private String signupKey;
}
