package woowacourse.shoppingcart.exception;

public class InvalidMemberException extends RuntimeException {
    public InvalidMemberException() {
        this("존재하지 않는 유저입니다.");
    }

    public InvalidMemberException(final String msg) {
        super(msg);
    }
}
