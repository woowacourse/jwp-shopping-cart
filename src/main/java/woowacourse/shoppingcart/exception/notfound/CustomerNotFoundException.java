package woowacourse.shoppingcart.exception.notfound;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException() {
        super("유저를 찾을 수 없습니다.");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
