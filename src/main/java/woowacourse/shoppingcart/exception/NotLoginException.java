package woowacourse.shoppingcart.exception;

public class NotLoginException extends RuntimeException {

    public NotLoginException() {
        super("로그인되지 않은 사용자 입니다.");
    }
}
