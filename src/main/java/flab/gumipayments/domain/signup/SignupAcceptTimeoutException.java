package flab.gumipayments.domain.signup;

public class SignupAcceptTimeoutException extends RuntimeException {
    public SignupAcceptTimeoutException() {
        super();
    }

    public SignupAcceptTimeoutException(String message) {
        super(message);
    }
}
