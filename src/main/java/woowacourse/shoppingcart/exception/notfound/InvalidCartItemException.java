package woowacourse.shoppingcart.exception.notfound;

public class InvalidCartItemException extends NotFoundException {

    public InvalidCartItemException() {
        super("유효하지 않은 장바구니입니다.");
    }
}
