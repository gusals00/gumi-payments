package flab.gumipayments.application.apikey;

public class ApiKeyIssueException extends RuntimeException{

    public ApiKeyIssueException() {
        super();
    }

    public ApiKeyIssueException(String message) {
        super(message);
    }
}
