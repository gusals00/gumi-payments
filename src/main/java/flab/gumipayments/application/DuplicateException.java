package flab.gumipayments.application;

public class DuplicateException extends RuntimeException {

    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }
}
