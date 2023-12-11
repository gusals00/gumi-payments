package flab.gumipayments.application;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import flab.gumipayments.support.SystemException;

public class DuplicateSystemException extends SystemException {


    public DuplicateSystemException(SystemErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DuplicateSystemException(SystemErrorCode errorCode){
        super(errorCode,"no message");
    }
}
