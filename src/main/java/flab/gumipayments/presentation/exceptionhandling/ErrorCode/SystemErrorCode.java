package flab.gumipayments.presentation.exceptionhandling.ErrorCode;

public enum SystemErrorCode implements ErrorCode{
    NOT_FOUND,
    DUPLICATED;

    @Override
    public String toString() {
        return "SYSTEM_" + super.toString();
    }
}
