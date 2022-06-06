package woowacourse.shoppingcart.exception;

import io.jsonwebtoken.JwtException;

public class TokenExpiredException extends JwtException {

    public TokenExpiredException() {
        super("토큰이 만료되었습니다.");
    }
}
