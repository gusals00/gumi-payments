package flab.gumipayments.application;

public class ApiKeyIssueException extends RuntimeException{

    public ApiKeyIssueException() {
        super();
    }

    public ApiKeyIssueException(String message) {
        super(message);
    }
}
