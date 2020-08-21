package org.greenbyme.angelhack.exception;

public class PersonalMissionException extends RuntimeException {

    private final ErrorCode errorCode;

    public PersonalMissionException(final String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PersonalMissionException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
