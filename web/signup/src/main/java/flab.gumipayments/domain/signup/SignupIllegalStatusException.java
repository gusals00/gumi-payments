package flab.gumipayments.domain.signup;

public class SignupIllegalStatusException extends RuntimeException {
    public SignupIllegalStatusException() {
        super();
    }

    public SignupIllegalStatusException(String message) {
        super(message);
    }
}
