package woowacourse.shoppingcart.exception.badrequest;

public class InvalidEmailException extends BadRequestException {

    public InvalidEmailException() {
        super("997", "이메일이 유효하지 않습니다.");
    }
}
