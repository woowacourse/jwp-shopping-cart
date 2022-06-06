package woowacourse.shoppingcart.application.exception;

public final class InvalidCustomerException extends ValidatedException {
    public InvalidCustomerException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidCustomerException(final String msg) {
        super(msg);
    }
}
