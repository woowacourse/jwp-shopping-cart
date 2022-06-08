package woowacourse.shoppingcart.exception.bodyexception;

public class IllegalPasswordException extends IllegalRequestException {

    public IllegalPasswordException() {
        super("1000", "비밀번호 양식이 잘못 되었습니다.");
    }
}
