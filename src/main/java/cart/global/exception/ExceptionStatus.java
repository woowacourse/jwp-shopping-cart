package cart.global.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionStatus {

    BAD_INPUT_VALUE_EXCEPTION(400, "입력 값이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_PRODUCT(404, "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_EXCEPTION(500, "서버가 응답할 수 없습니다", HttpStatus.INTERNAL_SERVER_ERROR);

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
