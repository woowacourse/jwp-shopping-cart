package woowacourse.shoppingcart.exception;

public class WrongPasswordException extends IllegalArgumentException {

    public WrongPasswordException() {
        super("비밀번호가 다릅니다.");
    }
}
