package woowacourse.shoppingcart.exception;

public class IllegalNicknameException extends IllegalRequestException {

    public IllegalNicknameException() {
        super("1000", "이메일 양식이 잘못 되었습니다.");
    }
}
