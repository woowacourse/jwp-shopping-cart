package woowacourse.shoppingcart.exception;

public class IllegalEmailException extends IllegalRequestException {

    public IllegalEmailException() {
        super("1000", "이메일 양식이 잘못 되었습니다.");
    }
}
