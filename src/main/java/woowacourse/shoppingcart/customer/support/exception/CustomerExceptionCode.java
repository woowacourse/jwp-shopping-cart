package woowacourse.shoppingcart.customer.support.exception;

import org.springframework.http.HttpStatus;

import woowacourse.support.exception.ShoppingCartExceptionCode;

public enum CustomerExceptionCode implements ShoppingCartExceptionCode {

    ALREADY_EMAIL_EXIST(2001, "이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_EMAIL(2101, "이메일이 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_NICKNAME(2102, "닉네임이 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_PASSWORD(2103, "비밀번호가 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    MISMATCH_EMAIL_OR_PASSWORD(2201, "이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    MISMATCH_PASSWORD(2202, "입력된 비밀번호가 현재 비밀번호와 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final long code;
    private final String message;
    private final HttpStatus httpStatus;

    CustomerExceptionCode(final long code, final String message, final HttpStatus httpStatus) {
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