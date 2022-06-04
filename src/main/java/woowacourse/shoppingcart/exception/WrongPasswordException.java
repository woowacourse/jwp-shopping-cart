package woowacourse.shoppingcart.exception;

public class WrongPasswordException extends IllegalArgumentException {

    public WrongPasswordException() {
        super("비밀번호가 올바르지 않습니다.");
    }
}
