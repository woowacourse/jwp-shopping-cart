package woowacourse.shoppingcart.exception;

public class OrderNotFoundException extends DataNotFoundException {

    private static final String MESSAGE = "주문 내역이 존재하지 않습니다..";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
