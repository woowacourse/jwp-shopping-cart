package woowacourse.shoppingcart.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        this("존재하지 않는 유저입니다.");
    }

    public IncorrectPasswordException(final String msg) {
        super(msg);
    }
}
