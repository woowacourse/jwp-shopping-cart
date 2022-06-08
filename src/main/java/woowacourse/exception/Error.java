package woowacourse.exception;

import org.springframework.http.HttpStatus;

public enum Error {

    NOT_FOUND(1001, "존재하지 않은 URL입니다.", HttpStatus.NOT_FOUND),
    EXPIRED_TOKEN(1002, "토큰의 유효 기간이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1003, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    NEED_AUTHORIZATION(1004, "인증이 필요한 접근입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL_FORMAT(2101, "이메일 형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_NICKNAME_FORMAT(2102, "닉네임 형식이 맞지 않습니다", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(2103, "비밀번호 형식이 맞지 않습니다", HttpStatus.BAD_REQUEST),
    DUPLICATED_CUSTOMER_EMAIL(2001, "이미 존재하는 이메일입니다", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(2201, "이메일 혹은 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(2202, "입력된 비밀번호가 현재 비밀번호와 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_PRODUCT(3001, "해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_QUANTITY(4101, "수량 형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_IN_CUSTOMER_CART_ITEM(4001, "해당 상품이 장바구니에 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_ORDER(5001, "존재하지 않는 주문입니다.", HttpStatus.NOT_FOUND);

    private int errorCode;
    private String message;
    private HttpStatus httpStatus;

    Error(int errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
