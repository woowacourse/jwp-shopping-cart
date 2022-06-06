package woowacourse.shoppingcart.exception.notfound;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("유저를 찾을 수 없습니다.");
    }
}
