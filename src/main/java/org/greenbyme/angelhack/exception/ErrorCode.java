package org.greenbyme.angelhack.exception;

public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C_001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C_002", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "C_003", "Access is Denied"),
    INTERNAL_SERVER_ERROR(500, "C_004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C_005", " Invalid Type Value"),
    ENTITY_NOT_FOUND(400, "C_006", " Entity Not Found"),
    WRONG_ACCESS(403, "C_007", "잘못된 접근"),


    // User
    MEMBER_DUPLICATED_EMAIL(409, "U_001", "중복된 이메일 가입시도."),
    MEMBER_DUPLICATED_NICKNAME(409, "U_002", "중복된 닉네임 가입시도."),
    UNSIGNED(400, "U_003", "등록되지 않은 이메일"),
    WRONG_PASSWORD(400, "U_004", "잘못된 패스워드"),
    UNSIGNED_USER(400, "U_005", "등록되지 않은 유저 idx"),

    //Mission
    INVALID_MISSION(400, "M_001", "등록되지 않은 미션 id"),

    //PersonalMission
    INVALID_PERSONAL_MISSION(400, "PM_001", "등록되지 않은 개인 미션 정보 id"),
    ALREADY_EXISTS_MISSION(409, "PM_002", "이미 진행중인 미션입니다"),
    ALREADY_EXISTS_SAME_DAY_MISSION(409, "PM_003", "이미 동일한 기간의 미션을 진행중 입니다"),

    //Post
    OVER_PROGRESS(400, "P_001", "전체 인증 횟수 초과"),
    OVER_CERIFICATION(400, "P_002", "하루 인증 횟수를 초과"),
    INVALID_POST(400, "P_003", "등록되지 않은 Post id");

    private final Integer status;
    private final String code;
    private final String message;

    ErrorCode(final Integer status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
