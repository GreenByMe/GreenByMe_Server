package org.greenbyme.angelhack.exception;

public class PostException extends RuntimeException {

    private final ErrorCode errorCode;

    public PostException(final String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PostException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
