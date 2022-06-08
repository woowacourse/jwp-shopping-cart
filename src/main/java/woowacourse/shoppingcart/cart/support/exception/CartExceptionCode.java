package woowacourse.shoppingcart.cart.support.exception;

import org.springframework.http.HttpStatus;

import woowacourse.support.exception.ShoppingCartExceptionCode;

public enum CartExceptionCode implements ShoppingCartExceptionCode {

    NO_SUCH_PRODUCT_EXIST(4001, "요청하신 상품이 장바구니에 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_FORMAT_QUANTITY(4002, "수량이 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST);

    private final long code;
    private final String message;
    private final HttpStatus httpStatus;

    CartExceptionCode(final long code, final String message, final HttpStatus httpStatus) {
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
