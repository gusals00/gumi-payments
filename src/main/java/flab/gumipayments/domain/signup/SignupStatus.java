package flab.gumipayments.domain.signup;

import lombok.Getter;

@Getter
public enum SignupStatus {
    SIGNUP_REQUEST("가입 요청"),
    TIMEOUT("미인증"),
    ACCEPT("인증");

    private final String message;

    SignupStatus(String message) {
        this.message = message;
    }
}
