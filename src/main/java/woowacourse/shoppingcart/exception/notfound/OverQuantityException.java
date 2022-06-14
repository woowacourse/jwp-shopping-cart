package woowacourse.shoppingcart.exception.notfound;

public class OverQuantityException extends NotFoundException {

    public OverQuantityException() {
        super("재고가 부족합니다.");
    }
}
