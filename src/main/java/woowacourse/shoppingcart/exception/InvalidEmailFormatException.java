package woowacourse.shoppingcart.exception;

public class InvalidEmailFormatException extends IllegalArgumentException {

    public InvalidEmailFormatException(String email) {
        super("입력하신 이메일(" + email + ")이 형식에 어긋납니다.");
    }
}
