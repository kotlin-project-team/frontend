package com.study.dongamboard.type

enum class ResponseStatus(val code: Int, val message: String) {
    SUCCESS(200, "요청을 성공적으로 처리했습니다."),
    CREATED(201, "생성/수정을 완료했습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED(401, "로그인이 필요합니다."),
    FORBIDDEN(403, "권한이 없습니다."),
    NOT_FOUND(404, "삭제되었거나 존재하지 않습니다."),
    CONFLICT(409, "이미 존재하는 값입니다."),
    INTERNET_SERVER_ERROR(500, "서버가 응답하지 않습니다."),
}