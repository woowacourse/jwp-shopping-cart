package cart.global.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionStatus {

    BAD_INPUT_VALUE_EXCEPTION(400, "입력 값이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_AUTHORIZATION_HEADER(400, "지원하지 않는 Authorization 헤더입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_AUTHORIZATION_HEADER(401, "인증 헤더를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_AUTHORIZATION(401, "인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_PRODUCT(404, "상품을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_CART(404, "카트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_CART_PRODUCT(404, "카트 안에 존재하지 않는 상품입니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_MEMBER(404, "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
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
