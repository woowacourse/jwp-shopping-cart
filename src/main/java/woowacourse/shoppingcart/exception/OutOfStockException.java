package woowacourse.shoppingcart.exception;

public class OutOfStockException extends IllegalArgumentException {
    public OutOfStockException() {
        super("재고보다 많은 수량을 주문할 수 없습니다");
    }
}
