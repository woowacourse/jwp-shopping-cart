package woowacourse.shoppingcart.exception.bodyexception;

public class InvalidLoginException extends IllegalRequestException {

    public InvalidLoginException() {
        super("1002", "로그인에 실패했습니다.");
    }
}
