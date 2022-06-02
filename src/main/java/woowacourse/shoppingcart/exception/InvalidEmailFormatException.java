package woowacourse.shoppingcart.exception;

public class InvalidEmailFormatException extends IllegalArgumentException {

    public InvalidEmailFormatException() {
        super("이메일이 형식에 어긋납니다.");
    }
}
