package org.greenbyme.angelhack.exception;

public class InvalidValueException extends BusinessException{

    public InvalidValueException(final ErrorCode errorCode){
        super(errorCode);
    }
}
