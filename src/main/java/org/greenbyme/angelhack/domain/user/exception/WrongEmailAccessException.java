package org.greenbyme.angelhack.domain.user.exception;

import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.InvalidValueException;

public class WrongEmailAccessException extends InvalidValueException {

    public WrongEmailAccessException() {
        super(ErrorCode.UNSIGNED);
    }
}
