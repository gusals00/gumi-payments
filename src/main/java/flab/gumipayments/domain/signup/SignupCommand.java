package flab.gumipayments.domain.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignupCommand {
    private String email;
    private String password;
    private String name;
}
