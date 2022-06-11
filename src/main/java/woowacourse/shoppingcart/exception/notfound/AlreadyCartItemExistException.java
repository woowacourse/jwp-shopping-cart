package woowacourse.shoppingcart.exception.notfound;

public class AlreadyCartItemExistException extends BadRequestException {

    public AlreadyCartItemExistException() {
        super("이미 담겨있는 상품입니다.");
    }
}
