package flab.gumipayments.infrastructure.apikey;

public class ApiKeyFormatException extends RuntimeException {
    public ApiKeyFormatException() {
    }

    public ApiKeyFormatException(String message) {
        super(message);
    }
}
