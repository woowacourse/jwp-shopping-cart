package woowacourse.shoppingcart.exception.invalid;

public class InvalidPasswordException extends InvalidException {

    public InvalidPasswordException() {
        super("비밀번호는 8~16자에 소문자, 대문자, 숫자, 특수문자가 1자 이상씩 들어가야합니다😉");
    }
}
