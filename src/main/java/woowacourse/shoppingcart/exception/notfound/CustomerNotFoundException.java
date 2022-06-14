package woowacourse.shoppingcart.exception.notfound;

public class CustomerNotFoundException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public CustomerNotFoundException() {
        this(MESSAGE);
    }

    public CustomerNotFoundException(final String msg) {
        super(msg);
    }
}
