package woowacourse.shoppingcart.exception;

public class InvalidPasswordFormatException extends IllegalArgumentException {

    public InvalidPasswordFormatException() {
        super("비밀번호가 형식에 어긋납니다.");
    }
}
