package woowacourse.shoppingcart.exception.badrequest;

public class InvalidNicknameException extends BadRequestException {

    public InvalidNicknameException() {
        super("997", "닉네임이 유효하지 않습니다.");
    }
}
