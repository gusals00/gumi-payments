package flab.gumipayments.application;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import flab.gumipayments.support.SystemException;

public class DuplicateException extends SystemException {


    public DuplicateException(SystemErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DuplicateException(SystemErrorCode errorCode){
        super(errorCode,"no message");
    }
}
