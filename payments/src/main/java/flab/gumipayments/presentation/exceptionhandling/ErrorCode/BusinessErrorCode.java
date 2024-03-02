package flab.gumipayments.presentation.exceptionhandling.ErrorCode;

public enum BusinessErrorCode implements ErrorCode{

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
