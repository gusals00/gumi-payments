package flab.gumipayments.application;

import flab.gumipayments.support.SystemException;

public class NotFoundSystemException extends RuntimeException implements SystemException {

    public NotFoundSystemException() {
        super();
    }

    public NotFoundSystemException(String message) {
        super(message);
    }

    public NotFoundSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundSystemException(Throwable cause) {
        super(cause);
    }
}
