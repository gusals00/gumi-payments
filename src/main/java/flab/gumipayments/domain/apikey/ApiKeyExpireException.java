package flab.gumipayments.domain.apikey;


public class ApiKeyExpireException extends RuntimeException {

    public ApiKeyExpireException() {
        super();
    }

    public ApiKeyExpireException(String message) {
        super(message);
    }
}
