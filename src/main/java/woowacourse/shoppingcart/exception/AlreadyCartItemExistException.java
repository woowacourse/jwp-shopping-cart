package woowacourse.shoppingcart.exception;

public class AlreadyCartItemExistException extends RuntimeException {

    public AlreadyCartItemExistException() {
        super("이미 담겨있는 상품입니다.");
    }
}
