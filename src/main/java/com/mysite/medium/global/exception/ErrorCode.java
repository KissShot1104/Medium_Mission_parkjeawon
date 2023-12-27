package com.mysite.medium.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    ENTITY_NOT_FOUND(400, "C_001", "지정한 Entity를 찾을 수 없습니다."),
    INVALID_INPUT_VALUE(400, "C_002", "적절하지 않은 요청 값입니다."),
    NOT_FOUND(404, "C_003", "존재하지 않는 값을 요청했습니다."),
    FAIL_LOGIN(400, "AU_001", "아이디 또는 패스워드가 틀립니다."),
    DUPLICATED_LOGIN_ID(400, "AU_002", "이미 존재하는 ID입니다."),
    DUPLICATED_EMAIL(400, "AU_003", "이미 존재하는 E-mail입니다."),
    INTERNAL_SERVER_ERROR(500, "C_004", "서버에 문제가 생겼습니다.");

    private final String code;
    private final String message;
    private final int status;
    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
