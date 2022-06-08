package woowacourse.shoppingcart.auth.support.exception;

import org.springframework.http.HttpStatus;

import woowacourse.support.exception.ShoppingCartExceptionCode;

public enum AuthExceptionCode implements ShoppingCartExceptionCode {

    EXPIRED_TOKEN(1002, "토큰의 유효 기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1003, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    REQUIRED_AUTHORIZATION(1004, "인증이 필요한 접근입니다.", HttpStatus.UNAUTHORIZED);

    private final long code;
    private final String message;
    private final HttpStatus httpStatus;

    AuthExceptionCode(final long code, final String message, final HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
