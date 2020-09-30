package org.greenbyme.angelhack.exception;

public class TagException extends RuntimeException {

    private final ErrorCode errorCode;

    public TagException(final String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TagException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
