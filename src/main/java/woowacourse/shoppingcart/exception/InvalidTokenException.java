package woowacourse.shoppingcart.exception;

import io.jsonwebtoken.JwtException;

public class InvalidTokenException extends JwtException {

    public InvalidTokenException() {
        super("잘못된 형식의 토큰입니다.");
    }
}
