package org.greenbyme.angelhack.exception;

public class MissionInfoException extends RuntimeException {

    private final ErrorCode errorCode;

    public MissionInfoException(final String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MissionInfoException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
