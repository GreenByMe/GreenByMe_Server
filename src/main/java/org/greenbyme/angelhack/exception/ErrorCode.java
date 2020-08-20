package org.greenbyme.angelhack.exception;

public enum ErrorCode {

    UNEXPECTED(0, "서버 내부 에러."),
    MEMBER_DUPLICATED_EMAIL(1000, "중복된 이메일 가입시도."),
    UNSIGNED(1100, "등록되지 않은 이메일"),
    WRONG_PASSWORD(1200, "잘못된 패스워드"),
    UNSIGNED_USER(1300, "등록되지 않은 유저 idx"),
    INVALID_MISSION(2000,"등록되지 않은 미션 id"),
    INVALID_PERSONAL_MISSION(2100,"등록되지 않은 개인 미션 정보 id"),
    OVER_PROGRESS(2200, "인증 횟수를 초과했습니다"),
    INVALID_POST(3000,"등록되지 않은 인증 id"),
    OVER_CERIFICATION(3100, "하루 인증 횟수를 초과했습니다"),
    WRONG_ACCESS(3200, "잘못된 접근입니다"),
    ALREADY_EXISTS_MISSION(3300 , "이미 진행중인 미션입니다"),
    ALREADY_EXISTS_SAME_DAY_MISSION(3400 , "이미 동일한 기간의 미션을 진행중 입니다");

    private final Integer code;
    private final String message;

    ErrorCode(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
