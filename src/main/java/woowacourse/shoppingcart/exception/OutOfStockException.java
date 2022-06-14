package woowacourse.shoppingcart.exception;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
        this("재고가 부족합니다.");
    }

    public OutOfStockException(final String message) {
        super(message);
    }
}
