package woowacourse.shoppingcart.exception.notfound;

public class OrdersNotFoundException extends NotFoundException {
    public OrdersNotFoundException() {
        super("주문을 찾을 수 없습니다.");
    }
}
