package flab.gumipayments.application;

import flab.gumipayments.SystemException;

public class DuplicateSystemException extends RuntimeException implements SystemException {
    public DuplicateSystemException() {
        super();
    }

    public DuplicateSystemException(String message) {
        super(message);
    }

    public DuplicateSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSystemException(Throwable cause) {
        super(cause);
    }
}