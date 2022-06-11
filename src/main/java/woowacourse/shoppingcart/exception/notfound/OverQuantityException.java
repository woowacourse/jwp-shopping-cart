package woowacourse.shoppingcart.exception.notfound;

public class OverQuantityException extends BadRequestException {

    public OverQuantityException() {
        super("재고가 부족합니다.");
    }
}
