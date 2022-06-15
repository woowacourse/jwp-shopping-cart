package woowacourse.support.exception;

public class ShoppingCartException extends RuntimeException {

    private final ShoppingCartExceptionCode exceptionCode;

    public ShoppingCartException(final ShoppingCartExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public ShoppingCartExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
