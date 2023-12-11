package flab.gumipayments.application;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import flab.gumipayments.support.SystemException;

public class NotFoundSystemException extends SystemException {

    public NotFoundSystemException(SystemErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotFoundSystemException(SystemErrorCode errorCode) {
        super(errorCode,"no message");
    }
}
