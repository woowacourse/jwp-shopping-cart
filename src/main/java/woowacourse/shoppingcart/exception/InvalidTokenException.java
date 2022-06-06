package woowacourse.shoppingcart.exception;

public class InvalidTokenException extends JwtTokenException {

    public InvalidTokenException() {
        super("잘못된 형식의 토큰입니다.");
    }
}
