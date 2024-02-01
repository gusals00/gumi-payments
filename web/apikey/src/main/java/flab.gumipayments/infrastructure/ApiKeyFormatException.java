package flab.gumipayments.infrastructure;

public class ApiKeyFormatException extends RuntimeException {
    public ApiKeyFormatException() {
    }

    public ApiKeyFormatException(String message) {
        super(message);
    }
}
