package org.greenbyme.angelhack.exception;

public class UserException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserException(final String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
