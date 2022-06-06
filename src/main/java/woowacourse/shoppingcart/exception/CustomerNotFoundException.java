package woowacourse.shoppingcart.exception;

public class CustomerNotFoundException extends IllegalArgumentException {

    public CustomerNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }
}
