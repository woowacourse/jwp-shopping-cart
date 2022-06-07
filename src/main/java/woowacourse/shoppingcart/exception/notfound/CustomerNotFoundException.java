package woowacourse.shoppingcart.exception.notfound;

public class CustomerNotFoundException extends NotFoundException {

    public CustomerNotFoundException() {
        this("존재하지 않는 유저입니다.");
    }

    public CustomerNotFoundException(final String msg) {
        super(msg);
    }
}
