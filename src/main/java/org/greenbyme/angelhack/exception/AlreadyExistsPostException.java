package org.greenbyme.angelhack.exception;

public class AlreadyExistsPostException extends RuntimeException {

    public AlreadyExistsPostException() {
        super("이미 완료한 미션입니다");
    }
}
