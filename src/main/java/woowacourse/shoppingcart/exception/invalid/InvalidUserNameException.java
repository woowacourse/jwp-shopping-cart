package woowacourse.shoppingcart.exception.invalid;

public class InvalidUserNameException extends InvalidException {

    public InvalidUserNameException() {
        super("이름은 5~20자에 소문자, 숫자, 언더바(_)만 사용가능합니다😉");
    }
}
