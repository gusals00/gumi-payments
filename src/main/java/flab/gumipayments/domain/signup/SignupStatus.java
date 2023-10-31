package flab.gumipayments.domain.signup;

import lombok.Getter;

@Getter
public enum SignupStatus {
    SIGNUP_REQUEST("가입 요청"),
    TIMEOUT("미인증"),
    ACCEPT("인증"),
    ACCOUNT_CREATED("계정 생성");

    private final String message;

    SignupStatus(String message) {
        this.message = message;
    }
}
