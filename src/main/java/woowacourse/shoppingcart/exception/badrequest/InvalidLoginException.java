package woowacourse.shoppingcart.exception.badrequest;

public class InvalidLoginException extends BadRequestException {

    public InvalidLoginException() {
        super("1002", "로그인에 실패했습니다.");
    }
}
