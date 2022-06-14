package woowacourse.shoppingcart.exception.notfound;

public class AlreadyCartItemExistException extends NotFoundException {

    public AlreadyCartItemExistException() {
        super("이미 담겨있는 상품입니다.");
    }
}
