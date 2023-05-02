package cart.global.exception.common;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public enum ExceptionStatus {

    INVALID_INPUT_VALUE_EXCEPTION(400, "유효하지 않는 입력 값입니다.", BAD_REQUEST),
    CAN_NOT_FOUND_ACCOUNT_EXCEPTION(400, "존재하지 않는 사용자입니다.", BAD_REQUEST),
    CAN_NOT_FOUNT_HEADER_EXCEPTION(400, "헤더의 값이 존재하지 않습니다.", BAD_REQUEST),
    INVALID_AUTHORIZATION_TYPE_EXCEPTION(401, "유효하지 않는 인증 형태입니다.", UNAUTHORIZED),
    DATABASE_EXCEPTION(500, "데이터베이스에 문제가 생겼습니다.", INTERNAL_SERVER_ERROR);

    private final int status;
    private final String message;
    private final HttpStatus httpStatus;

    ExceptionStatus(final int status, final String message, final HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
