package org.greenbyme.angelhack.exception;

public class MissionException extends RuntimeException {

    private final ErrorCode errorCode;

    public MissionException(final String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MissionException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
