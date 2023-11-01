package flab.gumipayments.domain.signup.exception;

public class IllegalSignupStatusException extends RuntimeException {
    public IllegalSignupStatusException() {
        super();
    }

    public IllegalSignupStatusException(String message) {
        super(message);
    }
}
