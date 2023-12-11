package flab.gumipayments.support;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{

    protected SystemErrorCode systemErrorCode;

    public SystemException(SystemErrorCode errorCode, String message){
        super(message);
        systemErrorCode = errorCode;
    }
}
