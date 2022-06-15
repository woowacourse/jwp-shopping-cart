package woowacourse.shoppingcart.exception;

public class TokenExpiredException extends JwtTokenException {

    public TokenExpiredException() {
        super("토큰이 만료되었습니다.");
    }
}
