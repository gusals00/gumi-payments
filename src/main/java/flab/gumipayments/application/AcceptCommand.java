package flab.gumipayments.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AcceptCommand {
    private String signupKey;
}
