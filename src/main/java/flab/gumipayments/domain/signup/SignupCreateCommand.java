package flab.gumipayments.domain.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class SignupCreateCommand {
    private String email;
    private LocalDateTime expireDate;
    private String signupKey;
}
