package flab.gumipayments.application;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import flab.gumipayments.support.SystemException;

public class NotFoundException extends SystemException {

    public NotFoundException(SystemErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public NotFoundException(SystemErrorCode errorCode) {
        super(errorCode,"no message");
    }
}
