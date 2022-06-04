package woowacourse.shoppingcart.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("로그인 후 사용이 가능합니다.");
    }
}
