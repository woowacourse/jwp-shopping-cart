package woowacourse.shoppingcart.exception.badrequest;

public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException() {
        super("997", "비밀번호가 유효하지 않습니다.");
    }
}
