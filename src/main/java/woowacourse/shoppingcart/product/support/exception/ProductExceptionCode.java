package woowacourse.shoppingcart.product.support.exception;

import org.springframework.http.HttpStatus;

import woowacourse.support.exception.ShoppingCartExceptionCode;

public enum ProductExceptionCode implements ShoppingCartExceptionCode {

    NO_SUCH_PRODUCT_EXIST(3001, "요청하신 상품은 전체 목록에 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final long code;
    private final String message;
    private final HttpStatus httpStatus;

    ProductExceptionCode(final long code, final String message, final HttpStatus httpStatus) {
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
