package woowacourse.shoppingcart.order.support.exception;

import org.springframework.http.HttpStatus;

import woowacourse.support.exception.ShoppingCartExceptionCode;

public enum OrderExceptionCode implements ShoppingCartExceptionCode {

    NO_SUCH_ORDER_EXIST(5001, "존재하지 않는 주문입니다.", HttpStatus.NOT_FOUND);

    private final long code;
    private final String message;
    private final HttpStatus httpStatus;

    OrderExceptionCode(final long code, final String message, final HttpStatus httpStatus) {
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
