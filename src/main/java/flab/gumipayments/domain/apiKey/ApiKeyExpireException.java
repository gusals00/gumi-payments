package flab.gumipayments.domain.apiKey;


public class ApiKeyExpireException extends RuntimeException {

    public ApiKeyExpireException() {
        super();
    }

    public ApiKeyExpireException(String message) {
        super(message);
    }
}
