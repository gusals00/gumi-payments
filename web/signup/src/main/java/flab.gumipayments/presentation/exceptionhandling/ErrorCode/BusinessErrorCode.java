package flab.gumipayments.presentation.exceptionhandling.ErrorCode;

public enum BusinessErrorCode implements ErrorCode{

    NOT_FOUND,
    TRY_AGAIN,
    INVALID_STATUS,
    TIMEOUT,
    BINDING,
    EXPIRED,
    INVALID_EXTEND;

    @Override
    public String toString() {
        return "BUSINESS_" + super.toString();
    }
}
