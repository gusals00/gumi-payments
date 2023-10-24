package flab.gumipayments.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AcceptCommand {
    private String signupKey;
    private String email;
}
