package woowacourse.shoppingcart.exception.badrequest;

public class InvalidLoginException extends NotFoundException {

    public InvalidLoginException() {
        super("1002", "로그인에 실패했습니다.");
    }
}
