package flab.gumipayments.infrastructure.apikey;

public class ApiKeyNotFoundException extends RuntimeException{
    public ApiKeyNotFoundException() {
        super();
    }

    public ApiKeyNotFoundException(String message) {
        super(message);
    }
}
