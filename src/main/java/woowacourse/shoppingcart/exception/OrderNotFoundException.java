package woowacourse.shoppingcart.exception;

public class OrderNotFoundException extends NotFoundException {

    private static final String ERROR_MESSAGE = "주문이 없습니다.";

    public OrderNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
