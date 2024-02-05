package flab.gumipayments.infrastructure.apikey;

public class ApiKeyExpiredException extends RuntimeException {
    public ApiKeyExpiredException() {
        super();
    }

    public ApiKeyExpiredException(String message) {
        super(message);
    }
}

